package com.ko.mediate.HC.AuthTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.tutoring.application.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenProviderTest {
  final String secret =
      "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";
  TokenProvider tokenProvider = new TokenProvider(secret, 3600, 604800);

  @DisplayName("토큰에 정보가 담기는지 테스트")
  @Test
  void createTokenTest() {
    String token =
        tokenProvider.createAccessToken(
            1L, "test@naver.com", RoleType.ROLE_TUTEE);
    UserInfo userInfo = tokenProvider.getUserInfoFromToken(token);
    assertThat(userInfo.getAccountId()).isEqualTo(1L);
    assertThat(userInfo.getAccountEmail()).isEqualTo("test@naver.com");
    assertThat(userInfo.getAuthority()).isEqualTo(RoleType.ROLE_TUTEE);
  }

  @DisplayName("토큰 만료 테스트")
  @Test
  void validateTokenTest() {
    TokenProvider expiredTokenProvider = new TokenProvider(secret, 0L, 0L);
    String expiredToken =
        expiredTokenProvider.createAccessToken(
            1L, "test@naver.com", RoleType.ROLE_USER);
    assertThatThrownBy(() -> tokenProvider.validateToken(expiredToken))
        .isInstanceOf(ExpiredJwtException.class);
  }

  @DisplayName("서명이 잘못된 토큰")
  @Test
  void invalidTokenTest() {
    String invalidToken = "asdf1234";
    assertThatThrownBy(() -> tokenProvider.validateToken(invalidToken))
        .isInstanceOf(io.jsonwebtoken.security.SecurityException.class);
  }
}
