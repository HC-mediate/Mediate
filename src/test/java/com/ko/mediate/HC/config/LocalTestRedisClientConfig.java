package com.ko.mediate.HC.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@TestConfiguration
public class LocalTestRedisClientConfig {

  @Bean
  @Profile("test")
  public RedisConnectionFactory redisConnectionFactory(){
    return new LettuceConnectionFactory("127.0.0.1", 6378);
  }
}
