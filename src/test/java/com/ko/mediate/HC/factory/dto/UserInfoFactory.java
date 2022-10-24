package com.ko.mediate.HC.factory.dto;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.tutoring.application.RoleType;

public class UserInfoFactory {
    public static String email = "test@naver.com";
    public static String nickname = "test_nickname";
    public static UserInfo createUserInfo(){
        return UserInfo.builder().accountId(1L)
                .accountNickname(nickname)
                .accountEmail(email)
                .roles(RoleType.ROLE_USER.name())
                .build();
    }
}
