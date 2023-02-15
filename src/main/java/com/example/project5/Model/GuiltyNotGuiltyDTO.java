package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuiltyNotGuiltyDTO {

    public GuiltyNotGuiltyDTO() {}

    String nickname="";
    Boolean guilty=false;
    String roomId="";

    public GuiltyNotGuiltyDTO(String nickname, Boolean guilty, String roomId) {
        this.nickname = nickname;
        this.guilty = guilty;
        this.roomId = roomId;
    }

    public GuiltyNotGuiltyDTO(String nickname, boolean guilty, String roomId) {
        this.nickname = nickname;
        this.guilty = guilty;

    }


}
