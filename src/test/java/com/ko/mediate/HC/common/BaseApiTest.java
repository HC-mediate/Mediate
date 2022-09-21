package com.ko.mediate.HC.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ko.mediate.HC.CommunityTest.S3MockConfig;
import com.ko.mediate.HC.auth.application.AuthService;
import com.ko.mediate.HC.auth.application.response.TokenDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.config.LocalRedisConfig;
import com.ko.mediate.HC.jwt.TokenStorage;
import com.ko.mediate.HC.tutee.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutor.infra.JpaTutorRepository;
import com.ko.mediate.HC.tutoring.application.RoleType;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ko.mediate.HC.auth.AccountFactory.createAccount;
import static com.ko.mediate.HC.auth.AccountFactory.createSignInDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({S3MockConfig.class, LocalRedisConfig.class})
@ActiveProfiles("test")
public class BaseApiTest {
    @Autowired
    protected JpaAccountRepository accountRepository;
    @Autowired
    protected JpaTutorRepository tutorRepository;
    @Autowired
    protected JpaTuteeRepository tuteeRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AmazonS3Client amazonS3Client;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private TokenStorage tokenStorage;

    private List<Account> accountResults;

    protected Account accountHasProfile, accountHasNoProfile;
    protected Map<Long, String> accessTokenMap = new HashMap<>();
    protected Map<Long, String> refreshTokenMap = new HashMap<>();
    protected final String AUTHORIZATION = "Authorization";

    @Value("${cloud.aws.s3.bucket}")
    protected String bucket;

    protected final String profileImageKey = "profile/test.jpg";
    protected final String tempFilePath = "temp.jpg";

    protected String saveEmail;
    protected Long saveId, normalUserId;
    protected String accessToken, refreshToken;
    protected String normalAccessToken;

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

        saveAccounts();
        authAccounts();
        amazonS3Client.putObject(new PutObjectRequest(bucket, profileImageKey, file));

        accountHasProfile = accountResults.get(0);
        accountHasNoProfile = accountResults.get(1);

        saveEmail = accountResults.get(0).getEmail();
        saveId = accountResults.get(0).getId();
        normalUserId = accountResults.get(1).getId();

        saveAccessAndRefreshToken();
    }

    private void authAccounts() {
        for (Account account : accountResults) {
            TokenDto dto = authService.signIn(createSignInDto(account.getEmail(), "1234"));
            accessTokenMap.put(account.getId(), dto.getAccessToken());
            refreshTokenMap.put(account.getId(), dto.getRefreshToken());
        }
    }

    private void saveAccessAndRefreshToken() {
        accessToken = accessTokenMap.get(saveId);
        refreshToken = refreshTokenMap.get(saveId);

        normalAccessToken = accessTokenMap.get(normalUserId);
    }

    private void saveAccounts() {
        accountResults =
                accountRepository.saveAllAndFlush(
                        List.of(
                                createAccount(
                                        "test1@naver.com",
                                        passwordEncoder.encode("1234"),
                                        "test_naver",
                                        profileImageKey,
                                        RoleType.ROLE_TUTOR.toString()),
                                createAccount(
                                        "test_normal@naver.com",
                                        passwordEncoder.encode("1234"),
                                        "test_normal",
                                        profileImageKey,
                                        RoleType.ROLE_USER.toString()),
                                createAccount(
                                        "test1@google.com",
                                        passwordEncoder.encode("1234"),
                                        "test1_google",
                                        RoleType.ROLE_TUTOR.toString())));
    }

    @AfterEach
    void afterEach() {
        clearFile();
        tutorRepository.deleteAllInBatch();
        tuteeRepository.deleteAllInBatch();
        accountRepository.deleteAllInBatch();
    }
}
