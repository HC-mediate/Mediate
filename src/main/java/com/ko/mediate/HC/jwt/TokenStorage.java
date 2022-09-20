package com.ko.mediate.HC.jwt;

public interface TokenStorage {
    void saveAccessToken(String accessToken, Long id);

    void saveRefreshToken(String refreshToken, Long id);

    String getAccessTokenById(Long id);

    String getRefreshTokenById(Long id);

    void deleteRefreshAndAccessTokenById(Long id);
}
