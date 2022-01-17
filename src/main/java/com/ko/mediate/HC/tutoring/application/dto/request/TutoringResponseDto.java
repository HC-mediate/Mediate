package com.ko.mediate.HC.tutoring.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TutoringResponseDto {
  @ApiModelProperty(value = "요청에 대한 응답을 보낼 계정 ID")
  private String toAccountId;
  @ApiModelProperty(value = "응답 유형 (수락/거절)")
  private TutoringResponseType responseType;
}
