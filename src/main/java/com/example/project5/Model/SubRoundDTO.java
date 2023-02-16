package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SubRoundDTO {// 얘도 json 아님
    // 경찬이코드
    int round;
    public Map<Integer,LogBaseDTO> subRoundList;

    public SubRoundDTO() {
    }


    public SubRoundDTO(int round, Map<Integer, LogBaseDTO> subRoundList) {
        this.round = round;
        this.subRoundList = subRoundList;
    }
}
