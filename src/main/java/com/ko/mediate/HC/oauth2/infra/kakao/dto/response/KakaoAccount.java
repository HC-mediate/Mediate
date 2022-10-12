package com.ko.mediate.HC.oauth2.infra.kakao.dto.response;

import lombok.Getter;

@Getter
public class KakaoAccount {

    private KakaoProfile profile;

    private String email;

    private String name;
}
