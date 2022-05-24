package com.ko.mediate.HC.AuthTest;

import com.ko.mediate.HC.auth.application.request.SignInDto;
import com.ko.mediate.HC.auth.application.request.SignUpDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutoring.application.RoleType;

public class AccountFactory {
  public static Account createAccount(String accountEmail, String name, String authority) {
    return Account.builder()
        .email(accountEmail)
        .password("1234")
        .name(name)
        .authority(authority)
        .phoneNum("010-1234-5678")
        .build();
  }

  public static Account createAccount(String email, String password, String name, String authority){
    return Account.builder()
      .email(email)
      .password(password)
      .name(name)
      .authority(authority)
      .phoneNum("010-1234-5678")
      .build();
  }

  public static SignInDto createSignInDto(String email, String password){
    return new SignInDto(email, password, RoleType.ROLE_USER, "token");
  }

  public static SignUpDto createSignUpDto(String email) {
    return new SignUpDto(email, "test", "test_nickname", "010-1234-5678");
  }
}
