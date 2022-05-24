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
  public void saveAccessToken(String accessToken, Long id) {
    redisTemplate
        .opsForValue()
        .set(
            ACCESS_KEY + SEPERATOR + String.valueOf(id),
            accessToken,
            tokenProvider.getExpiredTime(accessToken),
            TimeUnit.MILLISECONDS);
  }

  @Override
  public void saveRefreshToken(String refreshToken, Long id) {
    redisTemplate
        .opsForValue()
        .set(
            REFRESH_KEY + SEPERATOR + String.valueOf(id),
            refreshToken,
            tokenProvider.getExpiredTime(refreshToken),
            TimeUnit.MILLISECONDS);
  }

  @Override
  public String getAccessTokenById(Long id) {
    return (String) redisTemplate.opsForValue().get(ACCESS_KEY + SEPERATOR + String.valueOf(id));
  }

  @Override
  public void deleteRefreshAndAccessTokenById(Long id) {
    redisTemplate.delete(ACCESS_KEY + SEPERATOR + String.valueOf(id));
    redisTemplate.delete(REFRESH_KEY + SEPERATOR + String.valueOf(id));
  }
}
