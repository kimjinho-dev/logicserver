package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JurorpackDTO {

    String roomId="";
    List<String> nickNameList;
    String leader="";


    public JurorpackDTO(String roomId, List<String> nickNameList, String leader) {
        this.roomId = roomId;
        this.nickNameList = nickNameList;
        this.leader = leader;
    }


}
