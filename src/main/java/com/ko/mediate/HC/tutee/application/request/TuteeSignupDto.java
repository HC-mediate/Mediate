package com.ko.mediate.HC.tutee.application.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TuteeSignupDto {
  @ApiModelProperty(value = "학교 이름", required = true)
  @NotEmpty(message = "학교 이름을 입력해주세요")
  private String school;

  @ApiModelProperty(value = "학년", required = true)
  @NotEmpty(message = "학년을 입력해주세요")
  private String grade;

  @ApiModelProperty(value = "인문계/실업계 등등")
  private String major;

  @ApiModelProperty(value = "튜터링 가능 지역", required = true)
  @NotEmpty(message = "튜터링 가능 지역은 반드시 있어야 합니다.")
  private String address;

  @ApiModelProperty(value = "위도 좌표(Y)", required = true)
  @NotNull(message = "좌표값은 반드시 넣어야 합니다.")
  private Double latitude;

  @ApiModelProperty(value = "경도 좌표(X)", required = true)
  @NotNull(message = "좌표값은 반드시 넣어야 합니다.")
  private Double longitude;
}
