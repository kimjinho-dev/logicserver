package com.example.project5.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class StartFromDTO {

    List<UserDTO> playerList;
    String roomId;
    int round;
    int voteRound;

    public StartFromDTO(List<UserDTO> list, String roomId, int round, int voteRound) {
        this.playerList = list;
        this.roomId = roomId;
        this.round = round;
        this.voteRound = voteRound;
    }
}
