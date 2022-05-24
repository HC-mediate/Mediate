package com.ko.mediate.HC.AuthTest;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import static com.ko.mediate.HC.AuthTest.AccountFactory.*;

import com.ko.mediate.HC.HcApplicationTests;
import com.ko.mediate.HC.auth.application.request.SignUpDto;
import com.ko.mediate.HC.auth.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class SignUpApiTest extends HcApplicationTests {
  @Autowired private MockMvc mvc;
  final String existEmail = "test@google.com";

  @DisplayName("회원가입 테스트")
  @Test
  void signUpTest() throws Exception {
    // given
    SignUpDto dto = createSignUpDto("test@naver.com");

    // when, then
    mvc.perform(
            post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andDo(print());

    Account account = accountRepository.findAccountByEmail(dto.getEmail()).get();
    assertThat(account.getEmail()).isEqualTo(dto.getEmail());
    assertThat(account.getName()).isEqualTo(dto.getName());
    assertThat(account.getPhoneNum()).isEqualTo(dto.getPhoneNum());
  }

  @DisplayName("회원가입 이메일 중복 예외")
  @Test
  void validateEmailTest() throws Exception {
    // given
    SignUpDto dto = createSignUpDto(existEmail);

    // when, then
    mvc.perform(
            post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(containsString(existEmail)))
        .andDo(print());
  }
}
