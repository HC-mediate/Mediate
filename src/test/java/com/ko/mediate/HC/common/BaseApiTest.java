package com.ko.mediate.HC.common;

import static com.ko.mediate.HC.auth.AccountFactory.createAccount;
import static com.ko.mediate.HC.auth.AccountFactory.createSignInDto;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ko.mediate.HC.CommunityTest.S3MockConfig;
import com.ko.mediate.HC.auth.application.AuthService;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.config.LocalRedisConfig;
import com.ko.mediate.HC.tutoring.application.RoleType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({S3MockConfig.class, LocalRedisConfig.class})
@ActiveProfiles("test")
public class BaseApiTest {
  @Autowired protected JpaAccountRepository accountRepository;
  @Autowired private AuthService authService;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private AmazonS3Client amazonS3Client;

  private List<Account> accountResults;

  protected Account profileHasAccount;
  protected Map<Long, String> tokenMap = new HashMap<>();
  protected final String AUTHORIZATION = "Authorization";
  protected final String BEARER = "Bearer ";

  @Value("${cloud.aws.s3.bucket}")
  protected String bucket;

  protected final String profileImageKey = "profile/test.jpg";
  protected final String tempFilePath = "temp.jpg";

  private File createTempFile() throws IOException {
    File file = new File(tempFilePath);
    file.createNewFile();
    return file;
  }

  private void clearFile() {
    File file = new File(tempFilePath);
    file.delete();
  }

  @BeforeEach
  void beforeEach() throws IOException {
    File file = createTempFile();
    accountResults =
        accountRepository.saveAllAndFlush(
            List.of(
                createAccount(
                    "test1@naver.com",
                    passwordEncoder.encode("1234"),
                    "test_naver",
                    profileImageKey,
                    RoleType.ROLE_TUTOR.toString())));

    for (Account account : accountResults) {
      String token =
          authService.signIn(createSignInDto(account.getEmail(), "1234")).getAccessToken();
      amazonS3Client.putObject(new PutObjectRequest(bucket, profileImageKey, file));
      tokenMap.put(account.getId(), token);
    }

    profileHasAccount = accountResults.get(0);
  }

  @AfterEach
  void afterEach() {
    clearFile();
    accountRepository.deleteAllInBatch();
  }
}
