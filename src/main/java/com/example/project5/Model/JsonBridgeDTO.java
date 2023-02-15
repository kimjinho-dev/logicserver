package com.example.project5.Model;



import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Getter
@Setter

public class JsonBridgeDTO {

    public String roomId;
    public String roomStatus;
    public int round;
    public int voteRound;
    public String status;// 상태
    public String script;
    public int guilty;
    public int notGuilty;


    public JsonBridgeDTO(String roomId, String roomStatus, int round, int voteRound, String status, String script, int guilty, int notGuilty, List<UserDTO> playerList, List<PrevRoundDTO> prevRound, List<AgreeDisAgreeDTO> agreeDisagree) {
        this.roomId = roomId;
        this.roomStatus = roomStatus;
        this.round = round;
        this.voteRound = voteRound;
        this.status = status;
        this.script = script;
        this.guilty = guilty;
        this.notGuilty = notGuilty;
        this.playerList = playerList;
        this.prevRound = prevRound;
        this.agreeDisagree = agreeDisagree;
    }

    public List<UserDTO> playerList;// userlist


    public List<PrevRoundDTO> prevRound;// 승패기록용

    public List<AgreeDisAgreeDTO> agreeDisagree ;

    public JsonBridgeDTO() {

    }


}