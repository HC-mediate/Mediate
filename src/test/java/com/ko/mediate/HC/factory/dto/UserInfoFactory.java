package com.ko.mediate.HC.factory.dto;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.tutoring.application.RoleType;

public class UserInfoFactory {
    public static UserInfo createUserInfo(){
        return UserInfo.builder().accountId(1L)
                .accountNickname("test_nickname")
                .accountEmail("test@naver.com")
                .roles(RoleType.ROLE_USER.name())
                .build();
    }
}
