package com.ko.mediate.HC.oauth2.infra.naver.dto.response;

import com.ko.mediate.HC.oauth2.domain.Profile;

public class NaverProfileDto {

    private NaverProfile response;

    public Profile toProfile() {
        return Profile.of(response.getId(), response.getEmail(), response.getName());
    }
}
