package org.manthan.springredissample.config;

import org.manthan.springredissample.models.Post;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    RedisTemplate<Long, Post> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Long, Post> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}

