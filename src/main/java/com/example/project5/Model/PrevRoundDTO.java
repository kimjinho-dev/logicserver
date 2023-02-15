package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrevRoundDTO   {// 게임 한 판의 자체 승패 기록

    int round;
    String win;

    public PrevRoundDTO(int round, String win) {
        this.round = round;
        this.win = win;
    }
}
