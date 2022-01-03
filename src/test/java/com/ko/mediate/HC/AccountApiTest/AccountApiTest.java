package com.ko.mediate.HC.AccountApiTest;

import com.ko.mediate.HC.domain.Account;
import com.ko.mediate.HC.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountApiTest {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private AccountRepository accountRepository;
  private String accountEmail;
  @BeforeEach
  void setup(){
    Account account = new Account("arsgsg", "1234", "yun");
    accountEmail = account.getEmail();
    accountRepository.save(account);
  }
  @Test
  void lookupTest() throws Exception {
    mvc.perform(get("/api/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("arsgsg"))
        .andExpect(jsonPath("$.name").value("yun"))
        .andDo(print());
  }
}
