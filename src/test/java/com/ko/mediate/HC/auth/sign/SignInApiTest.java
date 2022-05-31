package com.ko.mediate.HC.auth.sign;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static com.ko.mediate.HC.auth.AccountFactory.*;

import com.ko.mediate.HC.auth.application.request.SignInDto;
import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.jwt.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("로그인 api 테스트")
public class SignInApiTest extends BaseApiTest {
  @Autowired MockMvc mvc;
  @Autowired PasswordEncoder passwordEncoder;
  @Autowired TokenProvider tokenProvider;

  @DisplayName("로그인 성공 테스트")
  @Test
  void loginTest() throws Exception {
    // given
    SignInDto dto = createSignInDto(saveEmail, "1234");

    // when, then
    mvc.perform(
            post("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.refreshToken").exists())
        .andExpect(jsonPath("$.accessToken").exists())
        .andDo(print());
  }

  @DisplayName("리프레쉬 토큰으로 액세스 토큰 발급하기")
  @Test
  void refreshTest() throws Exception {
    mvc.perform(post("/api/refresh").header("Refresh", refreshToken))
        .andExpect(status().isCreated());
  }

  @DisplayName("비밀번호가 틀리면, 예외 메시지를 보낸다")
  @Test
  void incorrectPasswordTest() throws Exception {
    // given
    SignInDto dto = createSignInDto(saveEmail, "아무비빌번호");

    // when, then
    mvc.perform(
            post("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(jsonPath("$.message").value("비밀번호가 일치하지 않습니다."))
        .andDo(print());
  }

  @DisplayName("이메일이 없으면 예외 메시지를 보낸다")
  @Test
  void emailNotFoundTest() throws Exception {
    // given
    SignInDto dto = createSignInDto("qwer@gmail.com", "1234");

    // when, then
    mvc.perform(
            post("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(jsonPath("$.message").exists())
        .andDo(print());
  }
}
