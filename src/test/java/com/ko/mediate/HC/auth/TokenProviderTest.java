package com.ko.mediate.HC.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.exception.MediateExpiredTokenException;
import com.ko.mediate.HC.common.exception.MediateInvalidTokenException;
import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.tutoring.application.RoleType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("토큰 생성 Bean 테스트")
public class TokenProviderTest {

    final String secret =
            "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";
    TokenProvider tokenProvider = new TokenProvider(secret, 3600, 604800);

    @Test
    void 토큰에_정보가_담긴_문자열을_비교한다() {
        RoleType role = RoleType.ROLE_TUTEE;
        String token = tokenProvider.createAccessToken(1L, "test@naver.com", "test", List.of(role));
        UserInfo userInfo = tokenProvider.getUserInfoFromToken(token);
        assertThat(userInfo.getAccountId()).isEqualTo(1L);
        assertThat(userInfo.getAccountEmail()).isEqualTo("test@naver.com");
        assertThat(userInfo.getAccountNickname()).isEqualTo("test");
        assertThat(userInfo.getRoles().contains(RoleType.ROLE_TUTEE)).isTrue();
    }

    @Test
    void 액세스_토큰_만료시_MediateExpiredTokenException을_던진다() {
        RoleType role = RoleType.ROLE_USER;
        TokenProvider expiredTokenProvider = new TokenProvider(secret, 0L, 0L);
        String expiredToken =
                expiredTokenProvider.createAccessToken(1L, "test@naver.com", "test", List.of(role));
        assertThatThrownBy(() -> tokenProvider.validateToken(expiredToken))
                .isInstanceOf(MediateExpiredTokenException.class);
    }

    @Test
    void 리프레쉬_토큰_만료시_MediateExpiredTokenException을_던진다() {
        RoleType role = RoleType.ROLE_USER;
        TokenProvider expiredTokenProvider = new TokenProvider(secret, 0L, 0L);
        String expiredToken =
                expiredTokenProvider.createRefreshToken(1L, "test@naver.com", "test",
                        List.of(role));
        assertThatThrownBy(() -> tokenProvider.validateToken(expiredToken))
                .isInstanceOf(MediateExpiredTokenException.class);
    }

    @Test
    void 유효한_토큰이면_재사용하고_그렇지_않으면_생성하여_반환한다() {
        String email = "test@naver.com";
        String nickname = "test";
        RoleType role = RoleType.ROLE_USER;
        TokenProvider expiredTokenProvider = new TokenProvider(secret, 0L, 0L);
        TokenProvider validTokenProvider = new TokenProvider(secret, 60000L, 60000L);

        String expiredToken = expiredTokenProvider.createAccessToken(1L, email, nickname,
                List.of(role));
        String validToken = validTokenProvider.createAccessToken(1L, email, nickname,
                List.of(role));

        assertThat(
                validTokenProvider.createAccessTokenIfExpired(
                        expiredToken, 1L, email, nickname, List.of(role)))
                .isNotEqualTo(expiredToken);
        assertThat(
                validTokenProvider.createAccessTokenIfExpired(
                        validToken, 1L, email, nickname, List.of(role)))
                .isEqualTo(validToken);
    }

    @Test
    void 서명이_잘못된_토큰은_MediateInvalidTokenException을_던진다() {
        String invalidToken = "asdf1234";
        assertThatThrownBy(() -> tokenProvider.validateToken(invalidToken))
                .isInstanceOf(MediateInvalidTokenException.class);
    }
}
