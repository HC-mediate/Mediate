package com.ko.mediate.HC.auth;

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

import static com.ko.mediate.HC.auth.AccountFactory.createAccount;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("Account")
@DataJpaTest
@Import(TestJpaConfig.class)
public class AccountRepositoryTest {
    @Autowired
    JpaAccountRepository accountRepository;

    @Test
    void 계정은_다수의_Role을_가질수_있다() {
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
