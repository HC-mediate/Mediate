package com.ko.mediate.HC.auth.domain;

import com.ko.mediate.HC.tutoring.application.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.ko.mediate.HC.auth.AccountFactory.createAccount;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AccountTest {
    @Tag("Account")
    @Test
    void Account에_권한이_추가된다() {
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
