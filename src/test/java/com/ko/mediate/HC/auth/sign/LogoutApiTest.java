package com.ko.mediate.HC.auth.sign;

import com.ko.mediate.HC.auth.application.AuthService;
import com.ko.mediate.HC.auth.application.dto.request.SignInDto;
import com.ko.mediate.HC.auth.application.dto.response.TokenDto;
import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.jwt.TokenStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static com.ko.mediate.HC.auth.AccountFactory.createSignInDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("로그아웃 테스트")
public class LogoutApiTest extends BaseApiTest {
    @Autowired
    AuthService authService;
    @Autowired
    MockMvc mvc;
    @Autowired
    TokenStorage tokenStorage;

    @Test
    void 로그아웃후_같은_토큰으로_로그인시_401을_던진다() throws Exception {
        // given
        SignInDto signInDto = createSignInDto(saveEmail, "1234");
        TokenDto dto = authService.signIn(signInDto);

        // when
        mvc.perform(delete("/api/logout").header(AUTHORIZATION, BEARER + dto.getAccessToken()))
                .andExpect(status().isOk());

        // then
        assertThat(tokenStorage.getAccessTokenById(saveId)).isNull();
        assertThat(tokenStorage.getRefreshTokenById(saveId)).isNull();

        mvc.perform(delete("/api/logout").header(AUTHORIZATION, BEARER + dto.getAccessToken()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
        mvc.perform(post("/api/refresh").header("Refresh", BEARER + dto.getRefreshToken()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
