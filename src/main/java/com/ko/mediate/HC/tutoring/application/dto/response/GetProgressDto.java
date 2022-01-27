package com.ko.mediate.HC.tutoring.application.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetProgressDto {
  @ApiModelProperty(value = "과정의 ID")
  private long id;
  @ApiModelProperty(value = "과정 내용")
  private String content;
  @ApiModelProperty(value = "완료 여부")
  private boolean isFinished;
}
