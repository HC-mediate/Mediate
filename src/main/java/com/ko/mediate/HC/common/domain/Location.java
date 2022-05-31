package com.ko.mediate.HC.common.domain;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
  @ApiModelProperty(value = "위도 좌표(X)", required = true)
  @NotNull(message = "좌표(위도)값은 반드시 넣어야 합니다.")
  private Double latitude;

  @ApiModelProperty(value = "경도 좌표(Y)", required = true)
  @NotNull(message = "좌표(경도)값은 반드시 넣어야 합니다.")
  private Double longitude;
}
