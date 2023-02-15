package com.example.project5.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash()
public class insideLogEntity {

    public String roomId;





}
