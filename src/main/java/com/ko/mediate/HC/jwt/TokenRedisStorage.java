package com.ko.mediate.HC.jwt;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRedisStorage implements TokenStorage {

    @Value("${jwt.access.validity-period}")
    private long accessTokenValidityPeriodInMillis;

    @Value("${jwt.refresh.validity-period}")
    private long refreshTokenValidityPeriodInMillis;

    private final StringRedisTemplate redisTemplate;

    private static final String SEPARATOR = "::";

    private static final String ACCESS_TOKEN_KEY = "accessToken";

    private static final String REFRESH_TOKEN_KEY = "refreshToken";

    @Override
    public void saveAccessToken(String accessToken, Long id) {
        redisTemplate
                .opsForValue()
                .set(
                        ACCESS_TOKEN_KEY + SEPARATOR + id,
                        accessToken,
                        accessTokenValidityPeriodInMillis,
                        TimeUnit.MILLISECONDS);
    }

    @Override
    public void saveRefreshToken(String refreshToken, Long id) {
        redisTemplate
                .opsForValue()
                .set(
                        REFRESH_TOKEN_KEY + SEPARATOR + id,
                        refreshToken,
                        refreshTokenValidityPeriodInMillis,
                        TimeUnit.MILLISECONDS);
    }

    @Override
    public String getAccessTokenById(Long id) {
        return redisTemplate.opsForValue().get(ACCESS_TOKEN_KEY + SEPARATOR + id);
    }

    @Override
    public String getRefreshTokenById(Long id) {
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_KEY + SEPARATOR + id);
    }

    @Override
    public void deleteRefreshAndAccessTokenById(Long id) {
        redisTemplate.delete(ACCESS_TOKEN_KEY + SEPARATOR + id);
        redisTemplate.delete(REFRESH_TOKEN_KEY + SEPARATOR + id);
    }
}
