package com.Gautam.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;


    @Disabled
    @Test
    void checkRedisConnection(){
        redisTemplate.opsForValue().set("email","mg123456@gmail.com");
        Object email =  redisTemplate.opsForValue().get("email");

        System.out.println(email);
    }
}
