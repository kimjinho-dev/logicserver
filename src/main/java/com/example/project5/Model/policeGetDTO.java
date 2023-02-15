package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class policeGetDTO {

    String nickname="";
    String roomId="";

    public policeGetDTO(String nickname, String roomId) {
        this.nickname = nickname;
        this.roomId = roomId;
    }
}
