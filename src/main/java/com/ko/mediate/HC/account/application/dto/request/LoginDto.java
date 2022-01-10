package com.ko.mediate.HC.account.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginDto {
  @NotEmpty
  @ApiModelProperty(example = "로그인 ID")
  private String id;
  @NotEmpty
  @ApiModelProperty(example = "로그인 Password")
  private String password;
}
