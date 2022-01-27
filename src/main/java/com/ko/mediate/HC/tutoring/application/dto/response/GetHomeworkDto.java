package com.ko.mediate.HC.tutoring.application.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetHomeworkDto {
  @ApiModelProperty(value = "숙제의 ID")
  private long id;

  @ApiModelProperty(value = "숙제 내용")
  private String content;

  @ApiModelProperty(value = "완료 여부")
  private boolean isFinished;
}
