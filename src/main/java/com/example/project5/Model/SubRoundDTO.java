package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubRoundDTO {// 얘도 json 아님

    String nickname;
    String vote ;
    boolean leader =false;
    boolean jury =  true;

    public SubRoundDTO(String nickname, String vote, boolean leader, boolean jury) {
        this.nickname = nickname;
        this.vote = vote;
        this.leader = leader;
        this.jury = jury;
    }
}
