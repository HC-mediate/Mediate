package com.ko.mediate.HC.AuthTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ko.mediate.HC.tutoring.domain.Account;
import com.ko.mediate.HC.tutoring.domain.Tutor;
import com.ko.mediate.HC.tutoring.infra.JpaAccountRepository;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class AuthTest {

  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private JpaAccountRepository accountRepository;
  @Autowired private MockMvc mvc;
  @Autowired ObjectMapper objectMapper;
  private static String password;
  private static int order = 1; // 매 시도마다 아이디를 다르게 하기 위함.

  @BeforeEach
  public void setup() {
    password = passwordEncoder.encode("test");
    Account account =
        new Account("test" + ++order, password, "USER");
    accountRepository.save(account);
  }

//  @Test
//  @DisplayName("올바른 로그인 시도")
//  public void validAuth() throws Exception {
//    String id = "test" + order;
//    String params = objectMapper.writeValueAsString(new LoginDto(id, "test"));
//    mvc.perform(post("/api/auth").contentType(MediaType.APPLICATION_JSON).content(params))
//        .andExpect(status().isOk())
//        .andDo(print());
//  }
//
//  @Test
//  @DisplayName("아이디, 비번 틀린 시도")
//  public void invalidAuth() throws Exception {
//    String id = "test" + order;
//    String params = objectMapper.writeValueAsString(new LoginDto(id, "1234"));
//    mvc.perform(post("/api/auth").contentType(MediaType.APPLICATION_JSON).content(params))
//        .andExpect(status().isBadRequest())
//        .andDo(print());
//    id = "test1" + order;
//    params = objectMapper.writeValueAsString(new LoginDto(id, "1234"));
//    mvc.perform(post("/api/auth").contentType(MediaType.APPLICATION_JSON).content(params))
//        .andExpect(status().isBadRequest())
//        .andDo(print());
//  }
}
