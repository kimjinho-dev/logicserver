package com.example.project5.Entity;


import com.example.project5.Model.ResultUserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
// 게임이 끝나고 게임 결과를 DB저장할때 사용할 Entity
public class GameResultLogEntity {
    // 게임결과 저장시 별도의 id 필요
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long playGameId;

    // 게임을 진행한 유저닉네임과 직업을 함께 넣어주기
    @OneToMany
    private List<ResultUser> players;

    // 게임 승패
    private boolean isWin;

    @Entity
    static class ResultUser {
        @Id
        private String nickname;
        @Column
        private String job;
    }

}
