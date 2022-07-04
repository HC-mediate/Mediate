package com.ko.mediate.HC.auth.sign;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static com.ko.mediate.HC.auth.AccountFactory.*;

import com.ko.mediate.HC.auth.application.AuthService;
import com.ko.mediate.HC.common.exception.AccountIncorrectPasswordException;
import com.ko.mediate.HC.common.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SignInServiceTest extends BaseApiTest {
  @Autowired AuthService authService;

  @DisplayName("비밀번호가 틀린 예외")
  @Test
  void incorrectPasswordTest() {
    assertThatThrownBy(() -> authService.signIn(createSignInDto(saveEmail, "아무비밀번호")))
        .isInstanceOf(AccountIncorrectPasswordException.class);
  }
}
