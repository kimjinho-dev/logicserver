package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RoundLogDTO {// 이친구는 다른 친구
    // 경찬이코드
    int round;
    ArrayList<SubRoundDTO> RoundNow;
    Map<String, Integer> result;

    public RoundLogDTO(int round, ArrayList<SubRoundDTO> roundNow, Map<String, Integer> result) {
        this.round = round;
        RoundNow = roundNow;
        this.result = result;
    }



    public RoundLogDTO() {

    }






}
