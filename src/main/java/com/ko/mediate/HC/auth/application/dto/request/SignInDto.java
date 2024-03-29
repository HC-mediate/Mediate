package com.ko.mediate.HC.auth.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignInDto {
    @ApiModelProperty(value = "계정 이메일", required = true)
    @NotBlank(message = "계정 이메일은 반드시 있어야 합니다.")
    private String email;

    @ApiModelProperty(value = "계정 비밀번호", required = true)
    @NotBlank(message = "비밀번호는 반드시 있어야 합니다.")
    private String password;

    @ApiModelProperty(value = "fcm token 값", required = true)
    @NotBlank(message = "fcm 토큰은 반드시 있어야 합니다.")
    private String fcmToken;
}
