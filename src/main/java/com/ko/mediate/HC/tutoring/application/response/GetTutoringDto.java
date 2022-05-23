package com.ko.mediate.HC.tutoring.application.response;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetTutoringDto {
  @ApiModelProperty(value = "튜터링 ID")
  private long tutoringId;

  @ApiModelProperty(value = "튜터링 이름")
  private String tutoringName;

  @ApiModelProperty(value = "시작 일자")
  private String startedAt;

  @ApiModelProperty(value = "진행 주차")
  private long doingWeek;

  @ApiModelProperty(value = "총 주차")
  private long totalWeek;
}
