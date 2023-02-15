package com.example.project5.Entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    private String user_id;


    @Column(length =8 , nullable = false)
    private String nick_name;

    @Column(length = 45, nullable = false)
    private String user_real_id;


    @Builder
    public UserEntity(String user_id, String nick_name , String user_real_id){

        this.user_id= user_id;
        this.nick_name=nick_name;
        this.user_real_id= user_real_id;

    }


}
