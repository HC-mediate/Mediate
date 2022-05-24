package com.ko.mediate.HC.jwt;

public interface TokenStorage {
  void saveAccessToken(String accessToken, String id);
  void saveRefreshToken(String refreshToken, String id);
  String getAccessTokenById(String id);
}
