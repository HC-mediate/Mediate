package com.ko.mediate.HC.tutee.application.request;

import com.ko.mediate.HC.tutoring.application.RoleType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TuteeSignupDto {
  @ApiModelProperty(value = "계정 ID", required = true)
  @NotEmpty(message = "ID는 반드시 있어야 합니다.")
  private String id;

  @ApiModelProperty(value = "계정 비밀번호", required = true)
  @NotEmpty(message = "비밀번호는 반드시 있어야 합니다.")
  private String password;

  @ApiModelProperty(value = "사용자 이름", required = true)
  @NotEmpty(message = "사용자 이름은 필수입니다.")
  private String name;

  @ApiModelProperty(value = "사용자 전화번호", required = true)
  @NotEmpty(message = "전화번호는 필수입니다.")
  private String phoneNum;

  @ApiModelProperty(value = "학교 이름", required = true)
  @NotEmpty(message = "학교 이름을 입력해주세요")
  private String school;

  @ApiModelProperty(value = "학년", required = true)
  @NotEmpty(message = "학년을 입력해주세요")
  private String grade;

  @ApiModelProperty(value = "튜터링 가능 지역", required = true)
  @NotEmpty(message = "튜터링 가능 지역은 반드시 있어야 합니다.")
  private String address;

  @ApiModelProperty(value = "튜터/튜티 타입", required = true)
  private RoleType type;

  @ApiModelProperty(value = "위도 좌표(Y)", required = true)
  @DecimalMin(value = "0.0", message = "유효하지 않은 좌표값입니다.")
  @NotNull(message = "좌표값은 반드시 넣어야 합니다.")
  private Double latitude;

  @ApiModelProperty(value = "경도 좌표(X)", required = true)
  @DecimalMin(value = "0.0", message = "유효하지 않은 좌표값입니다.")
  @NotNull(message = "좌표값은 반드시 넣어야 합니다.")
  private Double longitude;
}
