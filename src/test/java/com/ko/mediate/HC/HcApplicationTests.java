package com.ko.mediate.HC;

import static com.ko.mediate.HC.auth.AccountFactory.createAccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import com.ko.mediate.HC.homework.infra.JpaHomeworkRepository;
import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.jwt.TokenStorage;
import com.ko.mediate.HC.tutee.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutor.infra.JpaTutorRepository;
import com.ko.mediate.HC.tutoring.application.RoleType;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class HcApplicationTests {
  @Autowired protected ObjectMapper objectMapper;
  @Autowired protected JpaTutorRepository tutorRepository;
  @Autowired protected JpaTuteeRepository tuteeRepository;
  @Autowired protected JpaHomeworkRepository homeworkRepository;
  @Autowired protected JpaAccountRepository accountRepository;
  @Autowired protected JpaArticleRepository articleRepository;

  @Autowired private TokenProvider tokenProvider;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private TokenStorage tokenStorage;
  protected List<Account> accountResults;

  protected String refreshToken, accessToken;
  protected String saveEmail, savePassword;
  protected Long saveId;
  protected final String BEARER = "Bearer ";

  @BeforeEach
  void saveAccounts() {
    accountResults =
        accountRepository.saveAll(
            List.of(
                createAccount(
                    "test@google.com",
                    passwordEncoder.encode("1234"),
                    "test_nickname",
                    "ROLE_USER")));
    refreshToken =
        tokenProvider.createRefreshToken(
            accountResults.get(0).getId(), accountResults.get(0).getEmail(), RoleType.ROLE_USER);
    accessToken =
        tokenProvider.createAccessToken(
            accountResults.get(0).getId(), accountResults.get(0).getEmail(), RoleType.ROLE_USER);

    saveId = accountResults.get(0).getId();
    saveEmail = accountResults.get(0).getEmail();
    savePassword = accountResults.get(0).getPassword();

    tokenStorage.saveAccessToken(accessToken, saveId);
    tokenStorage.saveRefreshToken(refreshToken, saveId);
  }

  @AfterEach
  void deleteAccounts() {
    accountRepository.deleteAllInBatch();
  }
}
