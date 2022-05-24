package com.ko.mediate.HC.auth.application.request;

import com.ko.mediate.HC.tutoring.application.RoleType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignInDto {
  @ApiModelProperty(value = "계정 이메일")
  @NotBlank(message = "계정 이메일은 반드시 있어야 합니다.")
  private String accountEmail;

  @ApiModelProperty(value = "계정 비밀번호")
  @NotBlank(message = "비밀번호는 반드시 있어야 합니다.")
  private String password;

  @ApiModelProperty(value = "튜터/튜티 타입")
  private RoleType roleType;

  @ApiModelProperty(value = "fcm token 값")
  @NotBlank(message = "fcm 토큰은 반드시 있어야 합니다.")
  private String fcmToken;
}
