package com.ko.mediate.HC.tutoring.application.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TutoringResponseDto {
  @ApiModelProperty(value = "응답 유형 (수락/거절)")
  private TutoringResponseType responseType;
}
