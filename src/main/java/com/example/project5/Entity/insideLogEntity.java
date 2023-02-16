package com.example.project5.Entity;

import com.example.project5.Model.LogBaseDTO;
import com.example.project5.Model.RoundLogDTO;
import com.example.project5.Model.SubRoundDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@RedisHash()
public class insideLogEntity {

    @Id
    public String roomId;
    public int round;
    public ArrayList<Map<Integer, LogBaseDTO>> logs;
    public ArrayList<Map<String,Integer>> result;


    public insideLogEntity() {

    }

    public insideLogEntity(String roomId, int round, ArrayList<Map<Integer, LogBaseDTO>> logs, ArrayList<Map<String, Integer>> result) {
        this.roomId = roomId;
        this.round = round;
        this.logs = logs;
        this.result = result;
    }


    public RoundLogDTO toDTO(int round){

        RoundLogDTO temp = new RoundLogDTO();



        temp.setResult(this.result.get(round-1));
        temp.setRound(this.round);

        ArrayList<SubRoundDTO> tt = new ArrayList<>();

        for(int s=0;s<this.logs.get(round).keySet().size()
                ;s++){

            tt.get(s).setSubRoundList(logs.get(s));

        }

        temp.setRoundNow(tt);


        return temp;
    }





}
