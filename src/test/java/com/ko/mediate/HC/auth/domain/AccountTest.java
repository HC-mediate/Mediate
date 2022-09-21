package com.ko.mediate.HC.auth.domain;

import com.ko.mediate.HC.tutoring.application.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.ko.mediate.HC.auth.AccountFactory.createAccount;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {
    @Tag("Account")
    @DisplayName("Account에 권한이 추가된다.")
    @Test
    void addRoleTest() {
        // given
        Account tutor = createAccount("test", "1234", RoleType.ROLE_USER.toString());
        Account tutee = createAccount("test", "1234", RoleType.ROLE_USER.toString());

        // when
        tutor.joinTutor(null);
        tutee.joinTutee(null);

        // then
        assertThat(tutor.hasRole(RoleType.ROLE_TUTOR)).isTrue();
        assertThat(tutee.hasRole(RoleType.ROLE_TUTEE)).isTrue();
    }
}
