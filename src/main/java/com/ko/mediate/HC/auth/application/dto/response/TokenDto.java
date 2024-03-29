package com.ko.mediate.HC.auth.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class TokenDto {

    @ApiModelProperty(name = "리프레쉬 토큰")
    private String refreshToken;

    @ApiModelProperty(name = "액세스 토큰")
    private String accessToken;

    public TokenDto(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
