package com.example.project5.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {


    public UserDTO() {
    }
        String nickname="";
        String job="";
        Boolean isLeader=false;
        Boolean isJury=false;


    public UserDTO(String nickname, String job, Boolean isLeader, Boolean isJury) {
        this.nickname = nickname;
        this.job = job;
        this.isLeader = isLeader;
        this.isJury = isJury;
    }

    public UserDTO(String nickname){
        this.nickname = nickname;
        this.job = " ";
        this.isLeader = false;
        this.isJury = false;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "userNickname='" + nickname + '\'' +
                ", job='" + job + '\'' +
                ", isLeader=" + isLeader +
                ", isJury=" + isJury +
                '}';
    }
}
