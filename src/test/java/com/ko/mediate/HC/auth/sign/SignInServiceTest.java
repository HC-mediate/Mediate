package com.ko.mediate.HC.auth.sign;

import com.ko.mediate.HC.auth.application.AuthService;
import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.common.exception.AccountIncorrectPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ko.mediate.HC.auth.AccountFactory.createSignInDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SignInServiceTest extends BaseApiTest {
    @Autowired
    AuthService authService;

    @DisplayName("비밀번호가 틀린 예외")
    @Test
    void incorrectPasswordTest() {
        assertThatThrownBy(() -> authService.signIn(createSignInDto(saveEmail, "아무비밀번호")))
                .isInstanceOf(AccountIncorrectPasswordException.class);
    }
}
