package com.ko.mediate.HC.AuthTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ko.mediate.HC.auth.application.request.SignUpDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class AuthTest {

  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private JpaAccountRepository accountRepository;
  @Autowired private MockMvc mvc;
  @Autowired private ObjectMapper objectMapper;

  static SignUpDto signUp() {
    return new SignUpDto("test@naver.com", "test", "test_nickname", "010-1234-5678");
  }

  @DisplayName("회원가입 테스트")
  @Test
  void signUpTest() throws Exception {
    // given
    SignUpDto dto = signUp();

    // when, then
    mvc.perform(
            post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().is2xxSuccessful())
        .andDo(print());

    Account account = accountRepository.findAccountByEmail(dto.getEmail()).get();
    assertThat(account.getEmail()).isEqualTo(dto.getEmail());
    assertThat(account.getName()).isEqualTo(dto.getName());
    assertThat(account.getPhoneNum()).isEqualTo(dto.getPhoneNum());
  }
}
