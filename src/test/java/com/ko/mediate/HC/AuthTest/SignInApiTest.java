package com.ko.mediate.HC.AuthTest;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import static com.ko.mediate.HC.AuthTest.AccountFactory.*;

import com.ko.mediate.HC.HcApplicationTests;
import com.ko.mediate.HC.auth.application.request.SignInDto;
import com.ko.mediate.HC.auth.application.request.SignUpDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.jwt.TokenProvider;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

public class SignInApiTest extends HcApplicationTests {
  @Autowired MockMvc mvc;
  @Autowired PasswordEncoder passwordEncoder;
  @Autowired TokenProvider tokenProvider;

  @DisplayName("로그인 성공 테스트")
  @Test
  void loginTest() throws Exception {
    //given
    SignInDto dto = createSignInDto("test@google.com", "1234");

    //when, then
    mvc.perform(
            post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.refreshToken").exists())
        .andExpect(jsonPath("$.accessToken").exists());
  }

  @DisplayName("리프레쉬 토큰으로 액세스 토큰 발급하기")
  @Test
  void refreshTest() throws Exception {
    mvc.perform(
            post("/api/refresh")
                .header("Refresh", refreshToken)
                .header("Authorization", "Bearer: " + accessToken))
        .andExpect(status().isCreated());
  }
}
