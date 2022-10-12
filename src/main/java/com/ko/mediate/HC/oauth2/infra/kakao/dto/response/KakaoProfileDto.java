package com.ko.mediate.HC.oauth2.infra.kakao.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ko.mediate.HC.oauth2.domain.Profile;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoProfileDto {

    private Long id;

    private KakaoAccount kakaoAccount;

    public Profile toProfile() {
        return Profile.of(id.toString(), kakaoAccount.getEmail(), kakaoAccount.getName());
    }
}
