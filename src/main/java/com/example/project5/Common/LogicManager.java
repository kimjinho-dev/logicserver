package com.example.project5.Common;

import com.example.project5.Entity.RedisFormEntity;
import com.example.project5.Model.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;


@Getter
@Setter
@Service
public class LogicManager { //게임의 진행을 보조하는 사회자의 역할을 하는 코드 매 게임이 끝나면 로직매니저를 새로 다시 생성하는것을 추천

    private final int max_num = 6; // 사용자 최대 수
    public int big_round;//전체 라운드
    public int small_round;// 내부 배심원단 뽑기 라운드

    private int evil_win = 0;
    private int citizen_win = 0;// 승리횟수 기록
    public int ending = 0;// 새 방코드 발급 필요 여부

    public int LeaderIndex = -1;// 배심원장 인덱스

    private String room_id;// 진행 중인 게임 방의 고유 아이디




    public UserDTO PlayerListBefore[] = new UserDTO[max_num];// 사용자 목록 ID 받아온 거 저장할 곳

    public RedisFormEntity room_info;

    // 게임시작 - 직업분배 - 배심원단리더 선정 - 배심원단 꾸리기 - 찬반투표 - 유죄 무죄투표 - 결과 계산 - 반복 - 승패 갈림 - 멀린 찾기

    //사회자는 게임이 진행되는 방의 모든 정보를 기억하고 있어야 한다. -이는 곧 사회자가 생성되어지면서 요구되어질 데이터가 있다?

    // 필요정보 방 정보, 플레이어 정보, 라운드 진행정보, 라운드 로깅 정보,

    // 사회자는 먼저 각 방에 진행되는 게임의 유저를 모두 알고 있어야 한다.
    public ArrayList<HashMap<String, String>> playerList = new ArrayList<HashMap<String, String>>();
    // 6개의 큰 배열 안에 (유저 컨테이너) 고유 아이디를 key값으로 value는 사용자의 직업으로 지정하여 저장한다. ex) {<sub1234: police>,

    public HashMap<String, String> playerChoice = new HashMap<>();// 플레이어의 투표선택을 저장하는 곳
    public HashMap<String, String> playerJurar = new HashMap<>();// 플레이어의 배심원여부를 저장하는 곳

    public LogicManager() {
    }// 기본 빈 생성자


    public LogicManager(int big_round, int small_round, String room_id) {
        this.big_round = 1;
        this.small_round = 1;
        this.room_id = room_id;
        this.citizen_win = 0;
        this.evil_win = 0;
    }// 유무죄라운드 , 찬반투표라운드 , 플레이어 리스트 (arraylist안에 헤쉬맵 들어있는 구조)


    public void init_load(){




    }// 레디스에 저장한 게임사항을 불러오기
    public void ending_save(){



    }//현재 게임기록사항을 레디스에 저장하기


    public void startGame(String roomID) {

        LogicManager LM = new LogicManager(1, 1, roomID);
        LM.room_info.roomId=roomID;
        LM.room_info.round=1;
        LM.room_info.voteRound=1;
        LM.spreadJob();
        LM.findFirstLeader();

//            if (LM.big_round == 1 && LM.small_round == 1) {
//                LM.spreadJob();
//                LM.findFirstLeader();
//                // 이 때 한 번 프론트로 보내                   줘야함.
//
//            } else {// not first round
//                LM.findAfterLeader();// 수정 필요
//                // 이 때 한 번 프론트로 보내줘야함.
//            }
    }//게임 시작 알림.


    public void spreadJob() {// 직업을 랜덤으로 배분한다.
        int assassin = 1;
        int police = 1;
        int evil = 1;
        int citizens = 3;// 상수화 나중에 필요
        int flag_ev = 0;
        int flag_ino = 0;
        int cnt = 0;

        while (cnt != 6) {
            double coin = Math.floor(Math.random() * 2);// 0 또는 1 의 결과
            if (flag_ev == 1) {
                coin = 1;
            }// 이미 상대편이 다 뽑힌경우 다른편으로 개조한다.
            else if (flag_ino == 1) {
                coin = 0;
            }// 위와 같음

            switch ((int) coin) {

                case 0: // evil selected
                    if (assassin >= 1) {
                        assassin--;

                        room_info.playerList.get(cnt).setJob("assassin");

                    }// 맵에 직업과 유저 아이디 매핑해서 넣기
                    else if (evil >= 1) {
                        evil--;

                        room_info.playerList.get(cnt).setJob("evil");


                    } else if (evil + assassin < 1) {
                        flag_ev = 1;
                        continue;
                    }// 다 뽑혔는지 체크

                    break;

                case 1:// inocent selected
                    if (police >= 1) {
                        police--;

                        room_info.playerList.get(cnt).setJob("police");

                    } else if (citizens >= 1) {
                        citizens--;

                        room_info.playerList.get(cnt).setJob("citizens");
                    } else if (citizens + police < 1) {
                        flag_ino = 1;
                        continue;
                    }

                    break;

            }// switch end
            cnt++;
        }// while end
        // 세력 분할
    }// spread job end

    public void findFirstLeader() {
        double selectNum = Math.floor(Math.random() * 6);// 0~5 난수 정수생성
        LeaderIndex = (int) selectNum;// 형변환 후 인덱스 저장

        room_info.playerList.get(LeaderIndex).setIsLeader(true);

    }// 첫 원정대장을 랜덤 이 후는 시계 순서대로 부여


    public void findAfterLeader() {// 다음 원정대장의 아이디가 인자로 필요(프론트에서 시계방향 다음 친구를 줬으면 함
        for (int i = 0; i < max_num; i++) {
            if (room_info.playerList.get(i).getIsLeader().equals(true)) {
                LeaderIndex = i;
                break;
            }
        }// for end
        room_info.playerList.get(LeaderIndex).setIsLeader(false);
        if(LeaderIndex==0){
            LeaderIndex=6;
        }
        LeaderIndex--;
        room_info.playerList.get(LeaderIndex).setIsLeader(true);

        return ;
    }// anoLeadFunc end
    // 2번원정부터의 원정대장 찾기 시계방향 인수


    // 배심원 선택 정보를 재처리하여 인자로 넣어주면 실질적으로 활용할 변수에 입력


    public int voteJurors(HashMap<String, String> Jresult) {// Key : value @ map-> userid : true or  false
        int cnt = max_num;// 6명 =사람수

        //  for(int i=0;i<max_num;i++){
        for (String key : Jresult.keySet()) {
            playerChoice.put(key, Jresult.get(key));
            if (Jresult.get(key).equals("true") == true) {// 키값인 유저이름에 대해서 그 투표결과가 찬성이라면
            } else {
                cnt--;
            }
        }
        //  }// 정보가 언제 전달되는지?? 그저 배심원단이 선택 되었을 때? 아니면 선택된 이후에 그에 대한 투표에  대해서도 한 번?
        if (cnt >= max_num / 2 + 1) {

            return 2;
            // 배심원단 유죄무죄 진행 코드 입력필요

        } else {


            return -2;
        }

    }// 결과 기록후 정산해서 다음 단계 갈지 결정 결정 또는 초기화 코드 추가해야됨
    // 2와 -2로(찬반 결과) 결과를 반환함 1은 다른곳(승리조건반환)에 쓰이고 있기 때문에 구분하여서 사용함.


    public int initVote() {

        // 캐시관련 코드

        //로깅 관련 코드

        // 찬반 투표의 초기화 -> 대다수는 배심원단 구성에 대한 반대가 많은 경우 발생

        playerJurar = new HashMap<>();// 배심원단 해체
        playerChoice = new HashMap<>();// 선택결과 초기화
        small_round++;// 투표 기록 +1
        if (small_round == 5) {// 악의 세력 승리조건

            return -1;
        }
        return 0;// 계속 진행 하기

    }// 배심원단 찬반 투표 관련변수 초기화 캐쉬에 저장후 초기화 해야함 .

    public void initRound() {
        // 매 라운드별로 초기화 되어야 하는 것들을 초기화 하되 마지막라운드는 예외처리한다.

        if (this.evil_win == 3 || this.citizen_win == 3) {// 게임 1판 종료
            playerJurar = new HashMap<>();
            playerChoice = new HashMap<>();
            playerList = new ArrayList<HashMap<String, String>>();
            big_round = 1;
            small_round = 1;
            this.evil_win = 0;
            this.citizen_win = 0;
            //새로운 진행 방 코드 발급 필요
            this.ending = 1;

        } else {
            playerJurar = new HashMap<>();
            playerChoice = new HashMap<>();
            small_round = 1;
            big_round++;

        }
    }// 라운드 관련 변수 초기화

    public int guiltyChecker(HashMap<String, String> resultGuilt) { // key: user_id , value= result of judge {innocent,guilt}
        // int -1, 1반환하며 1은 시민세력의 승소 -1은 악의세력의 승소를 의미함.

        int innocent = 0;// 무죄나온 횟수 == 악의 세력만 가능함

        for (String key : resultGuilt.keySet()) {//키의 집합에 대해 반복문
            String res = resultGuilt.get(key);// value값을 먼저 받아서
            if (res.equals("innocent") == true) {// 일치하는지 체크
                innocent++;// 일치하면 증가
            }
        }
        if (innocent != 0) {// 악의 세력이 무죄를 넣음
            return -1;
        } else {// 만장일치 유죄시
            return 1;
        }
    }
    // 유무죄 계산기

    public void logger() {


    }

    public int findPolice(String target) {
        int cnt = 0;

        for (String key : playerList.get(cnt).keySet()) {//직업 불러오기

            if (playerList.get(cnt).equals(target)) {// 타겟의 아이디부터 찾기
                String res = playerList.get(cnt).get(key);//타겟 직업 확보
                if (res.equals("police")) {// 경찰인가?

                    return -1;// 악의 세력 승리
                }
                cnt++;// 진보된 'for'문
            }
        }
        return 1;// 응 아니야 시민승
    }

    public void save_info(){





    }
















}