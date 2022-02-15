package com.ko.mediate.HC.tutoring.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginDto {
  @ApiModelProperty(value = "계정 ID")
  @NotEmpty(message = "아이디는 반드시 들어가야 합니다.")
  private String id;

  @ApiModelProperty(value = "계정 ID")
  @NotEmpty(message = "비밀번호는 반드시 들어가야 합니다.")
  private String password;

  @ApiModelProperty(value = "firebase 기기 등록 토큰")
  @NotEmpty(message = "기기 등록 토큰은 반드시 들어가야 합니다.")
  private String fcmToken;
}
