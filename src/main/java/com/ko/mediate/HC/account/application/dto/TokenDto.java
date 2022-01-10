package com.ko.mediate.HC.account.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenDto {
  @ApiModelProperty(example = "응답 토큰 값")
  private String token;
}
