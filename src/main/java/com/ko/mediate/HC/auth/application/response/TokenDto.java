package com.ko.mediate.HC.auth.application.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class TokenDto {
  private static final String BEARER = "Bearer ";

  @ApiModelProperty(name = "리프레쉬 토큰")
  private String refreshToken;

  @ApiModelProperty(name = "액세스 토큰")
  private String accessToken;

  public TokenDto(String refreshToken, String accessToken) {
    this.refreshToken = BEARER + refreshToken;
    this.accessToken = BEARER + accessToken;
  }
}
