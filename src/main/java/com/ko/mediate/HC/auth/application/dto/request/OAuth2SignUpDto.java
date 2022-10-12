package com.ko.mediate.HC.auth.application.dto.request;

import com.ko.mediate.HC.oauth2.domain.SocialType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class OAuth2SignUpDto {

    @ApiModelProperty(value = "소셜 로그인 종류", required = true)
    private SocialType socialType;

    @ApiModelProperty(value = "access token 값", required = true)
    private String accessToken;

    @ApiModelProperty(value = "닉네임", required = true)
    private String nickname;

    @ApiModelProperty(value = "전화번호", required = true)
    private String phoneNum;
}
