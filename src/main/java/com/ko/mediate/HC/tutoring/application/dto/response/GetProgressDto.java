package com.ko.mediate.HC.tutoring.application.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetProgressDto {
  @ApiModelProperty(value = "Progress ID")
  private long progressId;

  @ApiModelProperty(value = "진행 주차")
  private long week;

  @ApiModelProperty(value = "진행 내용")
  private String content;

  @ApiModelProperty(value = "완료 여부")
  private Boolean isCompleted;
}
