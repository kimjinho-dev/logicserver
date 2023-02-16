package com.example.project5.Model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AgreeDisAgreeDTO {


    String nickname="";
    Boolean agree=false;

    public AgreeDisAgreeDTO() {
    }

    public AgreeDisAgreeDTO(String nickname, Boolean agree) {
        this.nickname = nickname;
        this.agree = agree;
    }
}
