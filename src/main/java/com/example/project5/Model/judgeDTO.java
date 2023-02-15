package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class judgeDTO {

    String nickName;
    Boolean guilty=false;
    String roomId="";


    public judgeDTO(String nickName, Boolean guilty, String roomId) {
        this.nickName = nickName;
        this.guilty = guilty;
        this.roomId = roomId;
    }







}
