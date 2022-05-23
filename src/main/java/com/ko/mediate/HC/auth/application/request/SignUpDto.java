package com.ko.mediate.HC.auth.application.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
  @ApiModelProperty(value = "계정 ID", required = true)
  @NotBlank(message = "ID는 반드시 있어야 합니다.")
  private String accountId;

  @ApiModelProperty(value = "계정 비밀번호", required = true)
  @NotBlank(message = "비밀번호는 반드시 있어야 합니다.")
  private String password;

  @ApiModelProperty(value = "닉네임", required = true)
  @NotBlank(message = "이름은 반드시 있어야 합니다.")
  private String nickname;

  @ApiModelProperty(value = "전화번호", required = true)
  @NotBlank(message = "전화번호는 반드시 있어야 합니다.")
  private String phoneNum;
}
