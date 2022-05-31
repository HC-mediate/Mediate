package com.ko.mediate.HC.auth;

import static com.ko.mediate.HC.auth.AccountFactory.*;
import static org.assertj.core.api.Assertions.*;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.config.TestJpaConfig;
import com.ko.mediate.HC.tutoring.application.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestJpaConfig.class)
public class AccountRepositoryTest {
  @Autowired JpaAccountRepository accountRepository;

  @Tag("Account")
  @DisplayName("리스트 형태의 Enum과 String 변환 테스트")
  @Test
  void manyRoleAccountTest() {
    // given
    Account account = createAccount("test@naver.com", "test", RoleType.ROLE_USER.toString());

    // when
    account.joinTutor(null);
    account.joinTutee(null);
    Long accountId = accountRepository.saveAndFlush(account).getId();

    // then
    Account result = accountRepository.findById(accountId).get();

    assertThat(result.getRoles()).contains(RoleType.ROLE_TUTEE, RoleType.ROLE_TUTOR);
  }
}
