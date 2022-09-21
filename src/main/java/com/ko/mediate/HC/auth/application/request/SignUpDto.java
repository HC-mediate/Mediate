package com.ko.mediate.HC.auth.application.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @ApiModelProperty(value = "이메일", required = true)
    @NotBlank(message = "이메일은 반드시 있어야 합니다.")
    @Email(message = "유효하지 않은 이메일입니다.")
    private String email;

    @ApiModelProperty(value = "계정 비밀번호", required = true)
    @NotBlank(message = "비밀번호는 반드시 있어야 합니다.")
    private String password;

    @ApiModelProperty(value = "이름", required = true)
    @NotBlank(message = "이름은 반드시 있어야 합니다.")
    private String name;

    @ApiModelProperty(value = "전화번호", required = true)
    @NotBlank(message = "전화번호는 반드시 있어야 합니다.")
    private String phoneNum;
}
