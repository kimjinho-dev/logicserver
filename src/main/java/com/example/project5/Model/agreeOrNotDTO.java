package com.example.project5.Model;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class agreeOrNotDTO {

    public agreeOrNotDTO() {}

    //프론트로부터 받을 양식임 6번 세야됨
    private String nickname="";
    private boolean vote=false;
    private String roomId="";

    public agreeOrNotDTO(String nickname, boolean vote, String roomId) {
        this.nickname = nickname;
        this.vote = vote;
        this.roomId = roomId;
    }
}