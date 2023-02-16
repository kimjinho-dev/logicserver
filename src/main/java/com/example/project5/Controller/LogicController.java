package com.example.project5.Controller;


import com.example.project5.Common.LoggingManager;
import com.example.project5.Common.LogicManager;
import com.example.project5.Entity.GameLogEntity;
import com.example.project5.Entity.GameResultLogEntity;
import com.example.project5.Entity.RedisFormEntity;
import com.example.project5.Entity.insideLogEntity;
import com.example.project5.Model.*;
import com.example.project5.Repo.LoggingFormRepository;
import com.example.project5.Repo.RedisFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LogicController {
    @Autowired
    private RedisFormRepository repo;
    @Autowired
    private LoggingFormRepository repols;

    private final SimpMessageSendingOperations messagingTemplate;

    @PostMapping(value = "/game/start/{roomId}")
    public ResponseEntity<RedisFormEntity> PostGameStart(@RequestBody StartFromDTO bodys, @PathVariable String roomId) {// parameter : roomID

        LogicManager LM = new LogicManager(1, 1, roomId);// 게임시작
        // body 에 레디스 초본 불러오기

        RedisFormEntity redisform = new RedisFormEntity(roomId);// 초본

//        System.out.println(bodys.getPlayerList().toString());


        //바디에 있는 데이터 세팅후
        redisform.setPlayerList(bodys.getPlayerList());
        redisform.setRoomStatus("Active");
        redisform.setStatus("makeJury");//? 정확한 키워드 필요
        redisform.setRoomId(roomId);
        redisform.setRound(1);
        redisform.setVoteRound(1);

//        System.out.println(bodys.getPlayerList().toString());

        // 기본세팅 설정후
        LM.setRoom_info(redisform);

        LM.spreadJob();// 직업분배 이 안에 jsondto의 roominfo가 변경됨
        LM.findFirstLeader();
//        if (bodys.getRound() == 1 && bodys.getVoteRound() == 1) {
//            LM.findFirstLeader();// 또한 안의 roominfo가 변경됨
//        } else {
//            LM.findAfterLeader();// 위와 같음   ??
//        }
//        System.out.println(LM.room_info.getPlayerList().toString());

        repo.save(LM.room_info);// 바로 레디스에 저장하기

//        System.out.println(repo.findById(LM.room_info.getRoomId()).get());//이게 뭐여
//        System.out.println(repo.findById(roomId).get().playerList.toString());
        messagingTemplate.convertAndSend("/sub/message/user/" + roomId, LM.room_info);

        return new ResponseEntity<RedisFormEntity>(LM.room_info, HttpStatus.ACCEPTED);

    }// 게임시작~ 대장선정~ 배심원단 선정 이전까지 진행

//배심원단 선택결과 브로드캐스팅 6인리스트 한번에

    @PostMapping(value = "/game/make/{roomId}")// 배심원단 구성 받기
    public ResponseEntity<JsonBridgeDTO> Postmansix(@RequestBody RedisFormEntity body, @PathVariable String roomId) {// parameter : roomID
        //6명의 선택결과를 한 번에 받아서
        //레디스 꺼내와서 라운드 정보 갱신해줘야됨.
//        System.out.println(body.playerList.toString());


        int rr = repo.findById(roomId).get().round;
        int vr = repo.findById(roomId).get().voteRound;
        // lm에 갱신후 찬반 결과  계산후 갱신 하고 들고와서
        LogicManager LM = new LogicManager(rr, vr, roomId);// 게임로직 호출
        LM.room_info = repo.findById(roomId).get();// 꺼내오기         //LM은 이전에 레디스 기록을 호출해와야함.
        LM.setRoom_info(repo.findById(roomId).get());// 프론트에서 준 결과 기반


        int cnt[] = {2, 3, 4, 3, 4};

        int limit = cnt[(repo.findById(roomId).get().round) - 1];
        int count = 0;
        for (int i = 0; i < 6; i++) {//  레디스 내부 정보의 플레이어 정보에 대하여
            LM.room_info.getPlayerList().get(i).setIsJury(false);// 초기화
            for (int s = 0; s < body.playerList.size(); s++) {
                // 만약 이 친구가 선택받은 친구면
                if (body.playerList.get(s).getNickname().equals(LM.room_info.getPlayerList().get(i).getNickname())) {
                    LM.room_info.getPlayerList().get(i).setIsJury(true);
                    break;
                }
            }
        }

        //  이제 이 사실이 담긴 lm의 인포를 bd해주어야함

        LM.room_info.setStatus("voteAgreeDisagree");
        repo.save(LM.room_info);// 일시저장
        // 소켓 브로드캐스트
        //Request(roomId,LM.room_info);// 솤켓으로 브로드전용
        messagingTemplate.convertAndSend("/sub/message/user/" + roomId, LM.room_info);

//        System.out.println(repo.findById(roomId).get());

        return new ResponseEntity<JsonBridgeDTO>(LM.room_info.toJSON(), HttpStatus.ACCEPTED);


        //return ResponseEntity.ok(body.toString());
    }//구성 배심원단 상태 전달받아서 저장 후 객체 저장 후 투표진행 마킹 요구 후 리턴

    // 원정대 찬성반대 투표결과 bd
//    @PostMapping(value = "/game/make/{roomId}")// param 안되면 pathvariable쓰기
//    public ResponseEntity<JsonBridgeDTO> getGameStart(@RequestBody List<AgreeDisAgreeDTO> body, @RequestParam String roomId){// parameter : roomID
//
//        // body내용 레디스 초안 불러와서 갱신 후 lm에 넘기기
//        // LogicManager LM= new LogicManager(1,1,room_id);// 게임시작
//       // @PathVariable String nicks;
//
//        int rr   = repo.findById(roomId).get().round;
//        int vr = repo.findById(roomId).get().voteRound;
//        LogicManager LM= new LogicManager(rr,vr,roomId);
//        LM.room_info= repo.findById(roomId).get();// 갱신
//
//        int vote_res=0;
//        for(int s=0;s<body.size();s++){
//           if(body.get(s).getVote()==true){
//               vote_res++;
//           }
//           else {
//           }
//        }
//        if(vote_res<3){
//
//            LM.room_info.setVoteRound(LM.room_info.getVoteRound()+1);
//
//            if(LM.room_info.getVoteRound()==5){
//
//                LM.room_info.setRound(LM.room_info.getRound()+1);// 악의세력 승리
//                LM.room_info.setRoomStatus("gameEnd");
//                repo.save(LM.room_info);
//
//            }
//            else{
//
//                LM.room_info.setRoomStatus("GuiltyBotguilty");
//
//            }
//
//        }

//
//        //return new ResponseEntity<JsonBridgeDTO>(, HttpStatus.ACCEPTED);
//
//
//    }//선택된 배심원단 1인에 대한?


//
    @PostMapping(value = "/game/insidelog/{round}/{roomId}")
    public ResponseEntity<RoundLogDTO> PostGetLogs(@RequestParam int round,@RequestParam  String roomId){// parameter :

        // 이전 로그들을 어디에 기록할 것인가? ->레디스에 단계마다 저장된 로그들을 풀어보자
        // 현재 라운드에 대한 로그들을 어떻게 기록하거나 바로 표시해줄 것인가?
        // 2라운드는 2까지의 토큰밖에 없으므로 제한에 대한 코딩 필요 없음.

        // 라운드가 끝나는 분기점에서 레디스에 기록

        LoggingManager LG= new LoggingManager();

        insideLogEntity ett=repols.findById("insidelog".concat(roomId)).get();

        return new ResponseEntity<RoundLogDTO>(ett.toDTO(round), HttpStatus.ACCEPTED);
        //return ResponseEntity.ok(body.toString());// tostring overriding해서 json형태 유지?
    }//로그 작업용 부분임.


    @PostMapping(value = "/game/firstvote/")
    public ResponseEntity<RedisFormEntity> PostVoteFirst(@RequestBody agreeOrNotDTO agreeData) throws InterruptedException {// parameter : roomID
        // 서비스 호출해서 addVote 실행
        String roomId = agreeData.getRoomId();
        RedisFormEntity redisFormEntity = repo.findById(roomId).get();

        // 초기에 빈값이거나, 6개(이전 투표 끝나고 초기화 안되어있을때) 초기화시켜주기
        if (redisFormEntity.agreeDisagree == null || redisFormEntity.agreeDisagree.size() == 6) {
            redisFormEntity.agreeDisagree = new ArrayList<AgreeDisAgreeDTO>();
        }
        redisFormEntity.agreeDisagree.add(new AgreeDisAgreeDTO(agreeData.getNickname(), agreeData.isVote()));

        if (redisFormEntity.agreeDisagree.size() == 6) {
            repo.save(redisFormEntity);
            // 찬성표합산
            List<AgreeDisAgreeDTO> votes = redisFormEntity.agreeDisagree;
            int agree = 0;
            for (int i = 0; i < 6; i++) {
                if (votes.get(i).getAgree()) {
                    agree += 1;
                }
            }

            // 투표, 라운드에 따른 분기나누기
            // 분기 나뉠때 차이인건 status
            if (agree > 3) {
                // 배심원단 구성 완료. 유무죄 투표 단계로 진행
                // 빅라운드 그대로, 스몰라운드 그대로
//                try{
//                    redisFormEntity.status = "resultAgreeDisagree";
//                    messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
//                    Thread.sleep(5000);
//                } catch(Exception e){
//                    redisFormEntity.status = "voteGuiltyNotGuilty";
//                    messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
//                }
                redisFormEntity.status = "resultAgreeDisagree";
                messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
//                Thread.sleep(5000);
                redisFormEntity.status = "voteGuiltyNotGuilty";
            } else {
                if (redisFormEntity.voteRound >= 5) {
                    redisFormEntity.status = "resultAgreeDisagree";
                    messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
//                    Thread.sleep(5000);
                    redisFormEntity.status = "resultGame";
                } else {
                    // 투표는 무산, 배심원단 구성 단계로 다시 돌아가야함
                    // 빅라운드 그대로, 스몰라운드 추가로
//                        try{
//                            redisFormEntity.status = "resultAgreeDisagree";
//                            messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
//                            Thread.sleep(5000);
//                            } catch(Exception e){
//                        redisFormEntity.voteRound += 1;
//                        redisFormEntity.status = "makeJury";
//                        for (UserDTO userDTO : redisFormEntity.playerList) {
//                            userDTO.setIsJury(false);
//                            }
//                        LogicManager LM = new LogicManager();
//                        LM.room_info = redisFormEntity;
//                        LM.findAfterLeader();
//                        redisFormEntity = LM.room_info;
//                        messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
//                        }
                            redisFormEntity.status = "resultAgreeDisagree";
                            messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
//                            Thread.sleep(5000);
                            redisFormEntity.voteRound += 1;
                            redisFormEntity.status = "makeJury";
                            for (UserDTO userDTO : redisFormEntity.playerList) {
                                userDTO.setIsJury(false);
                            }
                            LogicManager LM = new LogicManager();
                            LM.room_info = redisFormEntity;
                            LM.findAfterLeader();
                            redisFormEntity = LM.room_info;
                    }
            }
        }
        // 현재 상태 재저장
        repo.save(redisFormEntity);
        messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
        // 서비스에서 저장된값 가져오기(return값 사용하면 불필요)
        // 정상 응답 반환. 브로드캐스팅은 서비스단에서 처리
        return new ResponseEntity<RedisFormEntity>(redisFormEntity, HttpStatus.ACCEPTED);
    }//협의 후 api 형태 골라야 할듯  먼저 다 정렬해서 주는지 일일히 선택이 들어올 때 마다 할건지 분기점이 다름


    @PostMapping(value = "/game/secvote/")
    public ResponseEntity<RedisFormEntity> PostVoteSecond(@RequestBody GuiltyNotGuiltyDTO guiltyData) throws InterruptedException {// parameter : roomID
// 서비스 호출해서 addVote 실행
        String roomId = guiltyData.getRoomId();
        RedisFormEntity redisFormEntity = repo.findById(roomId).get();

//         초기에 빈값이거나, 6개(이전 투표 끝나고 초기화 안되어있을때) 초기화시켜주기
        if (redisFormEntity.prevRound == null) {
            redisFormEntity.prevRound = new ArrayList<PrevRoundDTO>();
        }

        if (guiltyData.getGuilty()) {
            redisFormEntity.guilty += 1;
        } else {
            redisFormEntity.notGuilty += 1;
        }

        PrevRoundDTO prevRoundDTO = new PrevRoundDTO(redisFormEntity.round, "");
        Integer guiltyCount = redisFormEntity.guilty + redisFormEntity.notGuilty;
        switch (redisFormEntity.round) {
            case 1:
                if (guiltyCount == 2) {
                    if (redisFormEntity.notGuilty > 0) {
                        prevRoundDTO.setWin("Lose");
                    } else {
                        prevRoundDTO.setWin("Win");
                    }
                    redisFormEntity.round += 1;
                    redisFormEntity.voteRound = 1;
                    redisFormEntity.notGuilty = 0;
                    redisFormEntity.guilty = 0;
                    redisFormEntity.prevRound.add(prevRoundDTO);
                    redisFormEntity.status = "resultGuiltyNotGuilty";
                }
                break;
            case 2:
            case 4:
                if (guiltyCount == 3) {
                    if (redisFormEntity.notGuilty > 0) {
                        prevRoundDTO.setWin("Lose");
                    } else {
                        prevRoundDTO.setWin("Win");
                    }
                    redisFormEntity.round += 1;
                    redisFormEntity.voteRound = 1;
                    redisFormEntity.notGuilty = 0;
                    redisFormEntity.guilty = 0;
                    redisFormEntity.prevRound.add(prevRoundDTO);
                    redisFormEntity.status = "resultGuiltyNotGuilty";
                }
                break;
            case 3:
            case 5:
                if (guiltyCount == 4) {
                    if (redisFormEntity.notGuilty > 0) {
                        prevRoundDTO.setWin("Lose");
                    } else {
                        prevRoundDTO.setWin("Win");
                    }
                    redisFormEntity.round += 1;
                    redisFormEntity.voteRound = 1;
                    redisFormEntity.notGuilty = 0;
                    redisFormEntity.guilty = 0;
                    redisFormEntity.prevRound.add(prevRoundDTO);
                    redisFormEntity.status = "resultGuiltyNotGuilty";
                }
                break;
        }

        Integer winCount = 0;
        Integer LoseCount = 0;
        for (PrevRoundDTO result : redisFormEntity.prevRound) {
            if (result.getWin().equals("Win")) {
                winCount += 1;
            } else {
                LoseCount += 1;
            }
        }

        messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
//        Thread.sleep(5000);
        // 유죄 3번시 -> 경찰투표
        if (winCount == 3) {
            redisFormEntity.status = "winCitizen";
        }

        // 무죄 3번시 -> 범죄자 승리로 결과
        if (LoseCount == 3) {
            redisFormEntity.status = "resultGame";
        }

        for (UserDTO userDTO : redisFormEntity.playerList) {
            userDTO.setIsJury(false);
        }

        // 현재 상태 재저장
        repo.save(redisFormEntity);
        messagingTemplate.convertAndSend("/sub/message/user/" + roomId, redisFormEntity);
        return new ResponseEntity<RedisFormEntity>(redisFormEntity, HttpStatus.ACCEPTED);
    }


    @PostMapping(value = "/game/policechoice/")
    public ResponseEntity<RedisFormEntity> policeChoice(@RequestBody policeGetDTO policeget) {
        RedisFormEntity redisFormEntity = repo.findById(policeget.getRoomId()).get();
        redisFormEntity.status = "resultGame";
        for (UserDTO userDTO : redisFormEntity.playerList) {
            if (userDTO.getJob().equals("police")) {
                if (userDTO.getNickname().equals(policeget.getNickname())) {
                    redisFormEntity.status = "successChoice";
                }
                break;
            }
        }
            repo.save(redisFormEntity);
            messagingTemplate.convertAndSend("/sub/message/user/" + policeget.getRoomId(), redisFormEntity);

            return new ResponseEntity<RedisFormEntity>(redisFormEntity, HttpStatus.ACCEPTED);

    }

//    public void saveInsideLog(String roomId, int round, int voteRound, insideLogEntity ett){
//        // 인자로 들어오는 ett는 반드시 모든 갱신이 이뤄진 후의 인자여야 작동하는 메소드임.
//
//        String redisLogPart= "insidelog".concat(roomId);//키값에 유니크성 부여
//        ett.roomId=redisLogPart;// 이러면 기본 레디스와 안겹친다 아마
//        repols.save(ett);
//
//    }// 라운드가 끝날 때 마다 ett의 갱신을 한다. 이 때 큰 라운드= 배열 인덱스 , 작은 라운드는 그냥 키값으로 부여해서 맵에 저장한다.
//    // 그리고 이를 레디스에 계속 업데이트 한다.


//    public void saveInsideLog(String roomId, int round, int voteRound){
//        // 인자로 들어오는 ett는 반드시 모든 갱신이 이뤄진 후의 인자여야 작동하는 메소드임.
//        RedisFormEntity redisFormEntity = repo.findById(roomId).get();
//        for (UserDTO userDTO : redisFormEntity.playerList) {
//            LogBaseDTO logBaseDTO = new LogBaseDTO(userDTO);
//            redisFormEntity.agreeDisagree.stream()
//                    .filter(a->a.getNickname().equals(userDTO.getNickname())).;
//            logBaseDTO.setVote();
//        }
//        insideLogEntity logData = new insideLogEntity();
//        logData.roomId="insidelog".concat(roomId);// 이러면 기본 레디스와 안겹친다 아마
//        logData.setRound(round);
//
//
//        repols.save(ett);
//
//    }// 라운드가 끝날 때 마다 ett의 갱신을 한다. 이 때 큰 라운드= 배열 인덱스 , 작은 라운드는 그냥 키값으로 부여해서 맵에 저장한다.
    // 그리고 이를 레디스에 계속 업데이트 한다.


}











//    public static void refresh_game_side(){
//
//
//
//
//    }// 받아온 정보를 바탕으로 로직매니저 내부의 정보를 갱신한다.
//
//    public static void refresh_transpart_side(){
//
//
//
//
//    }//진행된 게임 결과 - 로직매니저의 최신정보를 바탕으로 dto에 옮겨적은 후 전송 준비를 하는 작업



        // JSON 수신기->DTO

        //배심원단 관련 입출력 관리 JSON - 마지막 선택에 대한 거만 값이 올거같음

        //게임 시작 요청 들어옴  -> 로직에서도 게임시작 -> 플레이어 리스트를 받아서 번역 후 넘긴다.
        // 직업분배 메소드 실행직업 결과 매핑된 헤시맵 타입 자료구조 반환
        // 브로드 캐스팅에 대한 편리한 자료형 ? - 백간 통신 ~api ,
        // 꼭 JSON으로? 가능하면 JSON형태? -> STRING
        // 암살자? -> 암살자 한 번 픽해주고 그 결과 브로드캐스팅

        // 프론트 또는 비즈니스로 결과를 알리기
        // ---------------------------------------
        // 투표관련 선택 입력 json들어옴
        // 번역
        // 플레이어:선택됨여부 에 대한 json을 로직매니저에 넣어서 다음 단계 진행
        // 투표결과 받아와서 json으로 변환 후 브로드캐스팅을 위한 넘기기 (소켓또는 비즈니스)
        // url로 소켓 접근 가능?
        // 투표결과를 로직이 계산해서 라운드 진행 가능 여부 따져서 진행 정하기,
        // 이 경우에도 json의 형식을 더해야함
        // 투표 무결시 알리고 위에 것 반복 5회 이전까지c
        // 투표 성공시 선택된 인원에 대한 질의 와 결과를 계산한다.


        // 투표관련 입출력 관리 JSON

        // 데이터 로깅용 정리

        //
        //redis ?



