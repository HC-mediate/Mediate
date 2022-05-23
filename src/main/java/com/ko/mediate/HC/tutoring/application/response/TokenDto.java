package com.ko.mediate.HC.tutoring.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class TokenDto {
  String refreshToken;
  String accessToken;
}
