package com.example.project5.Entity;


import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
public class GameLogEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String PlayRoomId;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private int VoteRound;

    @Column(nullable = false)
    public String json;


}
