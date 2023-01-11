package com.tasc.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration

@ComponentScan("vn.tass.microservice.redis.dto")
@EnableRedisRepositories(basePackages = "vn.tass.microservice.redis.repository")
public class RedisPusherMessageService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(String message , ChannelTopic channelTopic){
        redisTemplate.convertAndSend(channelTopic.getTopic() , message);
    }
}
