package com.ko.mediate.HC.tutoring.application.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetTutoringDetailDto {
  @ApiModelProperty(value = "튜터링 이름")
  private String tutoringName;

  @ApiModelProperty(value = "시작 일자")
  private String startedAt;

  @ApiModelProperty(value = "진행 주차")
  private int doingWeek;

  @ApiModelProperty(value = "총 주차")
  private int totalWeek;
}
