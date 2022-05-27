package com.ko.mediate.HC.auth.application.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class TokenDto {
  private static final String BEARER = "Bearer ";
  String refreshToken;
  String accessToken;

  public TokenDto(String refreshToken, String accessToken) {
    this.refreshToken = BEARER + refreshToken;
    this.accessToken = BEARER + accessToken;
  }
}
