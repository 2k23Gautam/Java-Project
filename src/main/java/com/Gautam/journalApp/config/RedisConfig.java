package com.Gautam.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        //connection with redis
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //set seralizer for key
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //set seralizer fro value
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
