package com.ko.mediate.HC.AuthTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ko.mediate.HC.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class TokenProviderTest {
  final String secret =
      "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";
  TokenProvider tokenProvider = new TokenProvider(secret, 3600, 604800);
  Authentication authentication =
      new UsernamePasswordAuthenticationToken(
          "test@naver.com",
          "",
          List.of(
              new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_TUTOR")));

  @DisplayName("토큰에 정보가 담기는지 테스트")
  @Test
  void createTokenTest() {

    String token = tokenProvider.createAccessToken(1L, authentication);
    Claims claims = tokenProvider.decode(token);
    assertThat(claims.get("accountId")).isEqualTo(1);
    assertThat(claims.get("accountEmail")).isEqualTo("test@naver.com");
    List<String> authorities =
        Arrays.stream(claims.get("authority").toString().split(",")).collect(Collectors.toList());
    assertThat(authorities.contains("ROLE_USER"));
    assertThat(authorities.contains("ROLE_TUTOR"));
  }

  @DisplayName("토큰 만료 테스트")
  @Test
  void validateTokenTest() {
    TokenProvider expiredTokenProvider = new TokenProvider(secret, 0L, 0L);
    String expiredToken = expiredTokenProvider.createAccessToken(1L, authentication);
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
