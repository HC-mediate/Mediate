package com.ko.mediate.HC.auth;

import com.ko.mediate.HC.auth.application.dto.request.SignInDto;
import com.ko.mediate.HC.auth.application.dto.request.SignUpDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutoring.application.RoleType;

public class AccountFactory {
    static String phoneNum = "010-1234-5678";
    static String name = "test_name";
    static String password = "test";
    static String nickname = "test_nickname";

    public static Account createAccount(String accountEmail, String name, String role) {
        return Account.builder()
                .email(accountEmail)
                .password(password)
                .name(name)
                .role(RoleType.fromString(role))
                .phoneNum(phoneNum)
                .build();
    }

    public static Account createAccount(String email, String password, String name, String role) {
        return Account.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(RoleType.fromString(role))
                .phoneNum(phoneNum)
                .build();
    }

    public static Account createAccount(
            String email, String password, String name, String profileKey, String role) {
        return Account.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(RoleType.fromString(role))
                .phoneNum(phoneNum)
                .profileUrl("https://dummy.cloudfront.net/" + profileKey)
                .profileKey(profileKey)
                .build();
    }

    public static SignInDto createSignInDto(String email, String password) {
        return new SignInDto(email, password, "token");
    }

    public static SignUpDto createSignUpDto(String email) {
        return new SignUpDto(email, password, name, nickname, phoneNum);
    }

    public static SignUpDto createSignUpDtoWithNickName(String email, String nickname){
        return new SignUpDto(email, password, name, nickname, phoneNum);
    }
}
