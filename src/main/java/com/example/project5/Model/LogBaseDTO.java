package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

@Getter
@Setter
public class LogBaseDTO {

    String nickname="";
    Boolean vote=false;
    Boolean isLeader=false;
    Boolean isJury=false;

    public LogBaseDTO() {
    }

    public LogBaseDTO(String nickname, Boolean vote, Boolean isLeader, Boolean isJury) {
        this.nickname = nickname;
        this.vote = vote;
        this.isLeader = isLeader;
        this.isJury = isJury;
    }

    public LogBaseDTO(UserDTO userDTO) {
        this.nickname = userDTO.getNickname();
        this.isLeader = userDTO.getIsLeader();
        this.isJury = userDTO.getIsJury();
    }









}
