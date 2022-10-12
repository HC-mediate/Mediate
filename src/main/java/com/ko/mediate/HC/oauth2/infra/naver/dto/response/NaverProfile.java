package com.ko.mediate.HC.oauth2.infra.naver.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverProfile {

    private String id;

    private String name;

    private String email;

    private String profileImage;
}
