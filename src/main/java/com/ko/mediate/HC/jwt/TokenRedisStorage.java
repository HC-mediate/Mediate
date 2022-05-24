package com.ko.mediate.HC.jwt;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRedisStorage implements TokenStorage {

  private final RedisTemplate redisTemplate;
  private final TokenProvider tokenProvider;
  private final String SEPERATOR = "::";
  private final String ACCESS_KEY = "accessToken";
  private final String REFRESH_KEY = "refreshToken";

  @Override
  public void saveAccessToken(String accessToken, String id) {
    redisTemplate
        .opsForValue()
        .set(
            ACCESS_KEY + SEPERATOR + id,
            accessToken,
            tokenProvider.getExpiredTime(accessToken),
            TimeUnit.MILLISECONDS);
  }

  @Override
  public void saveRefreshToken(String refreshToken, String id) {
    redisTemplate
        .opsForValue()
        .set(
            REFRESH_KEY + SEPERATOR + id,
            refreshToken,
            tokenProvider.getExpiredTime(refreshToken),
            TimeUnit.MILLISECONDS);
  }

  @Override
  public String getAccessTokenById(String id) {
    return (String) redisTemplate.opsForValue().get(ACCESS_KEY + SEPERATOR + id);
  }
}
