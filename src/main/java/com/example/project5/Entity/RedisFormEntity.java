package com.example.project5.Entity;

import com.example.project5.Model.AgreeDisAgreeDTO;
import com.example.project5.Model.JsonBridgeDTO;
import com.example.project5.Model.PrevRoundDTO;
import com.example.project5.Model.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@RedisHash()
public class RedisFormEntity {


    @Id
    public String roomId;
    public String roomStatus;
    public int round;
    public int voteRound;
    public String status;// 상태
    public String script;
    public int guilty;
    public int notGuilty;

    public List<UserDTO> playerList;// userlist
    public List<PrevRoundDTO> prevRound;
    public List<AgreeDisAgreeDTO> agreeDisagree ;

    public RedisFormEntity() {
    }

    public RedisFormEntity(String roomId, String roomStatus, int round, int voteRound, String status, String script, int guilty, int notGuilty, List<UserDTO> playerList, List<PrevRoundDTO> prevRound, List<AgreeDisAgreeDTO> agreeDisagree) {
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

    public RedisFormEntity(String roomId) {
        this.roomId = roomId;
    }


    public JsonBridgeDTO toJSON() {

        JsonBridgeDTO temp= new JsonBridgeDTO();

        temp.setAgreeDisagree(this.getAgreeDisagree());
        temp.setGuilty(this.getGuilty());
        temp.setPlayerList(this.playerList);
        temp.setRound(this.round);
        temp.setStatus(this.status);
        temp.setScript(this.script);
        temp.setNotGuilty(this.notGuilty);
        temp.setPrevRound(this.prevRound);
        temp.setRoomId(this.roomId);
        temp.setVoteRound(this.voteRound);
        temp.setRoomStatus(this.roomStatus);

        return temp;



    }
}
