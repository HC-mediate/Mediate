package com.ko.mediate.HC.tutoring.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TuteeSignupDto {
  @ApiModelProperty(name = "계정 ID")
  private String id;
  @ApiModelProperty(name = "계정 비밀번호")
  private String password;
  @ApiModelProperty(name = "학교 이름")
  private String school;
  @ApiModelProperty(name = "학년")
  private String grade;
  @ApiModelProperty(name = "튜터링 가능 지역")
  private String address;
  @ApiModelProperty(name = "튜터/튜티 타입")
  private SignupType type;
}
