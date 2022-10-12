package com.ko.mediate.HC.auth;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.tutoring.application.RoleType;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("토큰 생성 Bean 테스트")
public class TokenProviderTest {
    final String secret =
            "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";
    TokenProvider tokenProvider = new TokenProvider(secret, 3600, 604800);

    @DisplayName("토큰에 정보가 담기는지 테스트")
    @Test
    void createTokenTest() {
        RoleType role = RoleType.ROLE_TUTEE;
        String token = tokenProvider.createAccessToken(1L, "test@naver.com", "test", List.of(role));
        UserInfo userInfo = tokenProvider.getUserInfoFromToken(token);
        assertThat(userInfo.getAccountId()).isEqualTo(1L);
        assertThat(userInfo.getAccountEmail()).isEqualTo("test@naver.com");
        assertThat(userInfo.getAccountNickname()).isEqualTo("test");
        assertThat(userInfo.getRoles().contains(RoleType.ROLE_TUTEE)).isTrue();
    }

    @DisplayName("액세스 토큰 만료 테스트")
    @Test
    void validateTokenTest() {
        RoleType role = RoleType.ROLE_USER;
        TokenProvider expiredTokenProvider = new TokenProvider(secret, 0L, 0L);
        String expiredToken =
                expiredTokenProvider.createAccessToken(1L, "test@naver.com", "test", List.of(role));
        assertThatThrownBy(() -> tokenProvider.isValidToken(expiredToken))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @DisplayName("리프레쉬 토큰 만료 테스트")
    @Test
    void validateRefreshTokenTest() {
        RoleType role = RoleType.ROLE_USER;
        TokenProvider expiredTokenProvider = new TokenProvider(secret, 0L, 0L);
        String expiredToken =
                expiredTokenProvider.createRefreshToken(1L, "test@naver.com", "test", List.of(role));
        assertThatThrownBy(() -> tokenProvider.isValidToken(expiredToken))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @DisplayName("유효한 토큰이면 재사용, 그렇지 않으면 생성")
    @Test
    void createAccessTokenIfExpiredTest() {
        String email = "test@naver.com";
        String nickname = "test";
        RoleType role = RoleType.ROLE_USER;
        TokenProvider expiredTokenProvider = new TokenProvider(secret, 0L, 0L);
        TokenProvider validTokenProvider = new TokenProvider(secret, 60000L, 60000L);

        String expiredToken = expiredTokenProvider.createAccessToken(1L, email, nickname, List.of(role));
        String validToken = validTokenProvider.createAccessToken(1L, email, nickname, List.of(role));

        assertThat(
                validTokenProvider.createAccessTokenIfExpired(
                        expiredToken, 1L, email, nickname, List.of(role)))
                .isNotEqualTo(expiredToken);
        assertThat(
                validTokenProvider.createAccessTokenIfExpired(
                        validToken, 1L, email, nickname, List.of(role)))
                .isEqualTo(validToken);
    }

    @DisplayName("서명이 잘못된 토큰이면 예외 발생")
    @Test
    void invalidTokenTest() {
        String invalidToken = "asdf1234";
        assertThatThrownBy(() -> tokenProvider.isValidToken(invalidToken))
                .isInstanceOf(io.jsonwebtoken.security.SecurityException.class);
    }
}
