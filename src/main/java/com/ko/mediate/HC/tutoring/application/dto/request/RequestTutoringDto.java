package com.ko.mediate.HC.tutoring.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestTutoringDto {
  @ApiModelProperty(value = "튜터링 이름")
  private String tutoringName;

  @ApiModelProperty(value = "튜터 ID")
  private String tutorId;

  @ApiModelProperty(value = "튜티 ID")
  private String tuteeId;
}
