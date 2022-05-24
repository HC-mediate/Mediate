package com.ko.mediate.HC.auth.application.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TokenDto {
  String refreshToken;
  String accessToken;
}
