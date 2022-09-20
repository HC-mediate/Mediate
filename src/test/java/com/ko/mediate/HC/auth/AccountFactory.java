package com.ko.mediate.HC.auth;

import com.ko.mediate.HC.auth.application.request.SignInDto;
import com.ko.mediate.HC.auth.application.request.SignUpDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutoring.application.RoleType;

public class AccountFactory {
    public static Account createAccount(String accountEmail, String name, String role) {
        return Account.builder()
                .email(accountEmail)
                .password("1234")
                .name(name)
                .role(RoleType.fromString(role))
                .phoneNum("010-1234-5678")
                .build();
    }

    public static Account createAccount(String email, String password, String name, String role) {
        return Account.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(RoleType.fromString(role))
                .phoneNum("010-1234-5678")
                .build();
    }

    public static Account createAccount(
            String email, String password, String name, String profileKey, String role) {
        return Account.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(RoleType.fromString(role))
                .phoneNum("010-1234-5678")
                .profileUrl("https://dummy.cloudfront.net/" + profileKey)
                .profileKey(profileKey)
                .build();
    }

    public static SignInDto createSignInDto(String email, String password) {
        return new SignInDto(email, password, "token");
    }

    public static SignUpDto createSignUpDto(String email) {
        return new SignUpDto(email, "test", "test_nickname", "010-1234-5678");
    }
}
