package com.ko.mediate.HC.oauth2.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Profile {

    private String socialId;

    private String email;

    private String name;

    public static Profile of(String socialId, String email, String name) {
        return new Profile(socialId, email, name);
    }
}
