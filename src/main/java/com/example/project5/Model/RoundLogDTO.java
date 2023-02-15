package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RoundLogDTO {// 이친구는 다른 친구

    int round;
    List<SubRoundDTO> RoundNow;

    public RoundLogDTO(int round, List<SubRoundDTO> roundNow) {
        this.round = round;
        RoundNow = roundNow;
    }






}
