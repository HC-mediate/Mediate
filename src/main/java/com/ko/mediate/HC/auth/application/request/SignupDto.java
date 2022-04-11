package com.ko.mediate.HC.auth.application.request;

import com.ko.mediate.HC.tutoring.application.RoleType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
  @ApiModelProperty(value = "계정 ID", required = true)
  @NotEmpty(message = "ID는 반드시 있어야 합니다.")
  private String id;

  @ApiModelProperty(value = "계정 비밀번호", required = true)
  @NotEmpty(message = "비밀번호는 반드시 있어야 합니다.")
  private String password;

  @ApiModelProperty(value = "이름", required = true)
  @NotEmpty(message = "이름은 반드시 있어야 합니다.")
  private String name;

  @ApiModelProperty(value = "튜터/튜티 타입", required = true)
  private RoleType roleType;

  @ApiModelProperty(value = "전화번호", required = true)
  @NotEmpty(message = "전화번호는 반드시 있어야 합니다.")
  private String phoneNum;
}
