package com.example.project5.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgreeDisAgreeDTO {


    String nickname="";
    Boolean vote=false;

    public AgreeDisAgreeDTO() {
    }

    public AgreeDisAgreeDTO(String nickname, Boolean vote) {
        this.nickname = nickname;
        this.vote = vote;
    }
}
