package com.ko.mediate.HC.tutoring.application.request;

import com.ko.mediate.HC.tutoring.application.RoleType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestTutoringDto {
  @ApiModelProperty(value = "튜터링 이름")
  @NotEmpty(message = "튜터링 이름은 반드시 있어야 합니다.")
  private String tutoringName;

  @ApiModelProperty(value = "튜터 이메일")
  @NotEmpty(message = "튜터의 이메일는 반드시 있어야 합니다.")
  @Email(message = "유효하지 않은 이메일입니다.")
  private String tutorEmail;

  @ApiModelProperty(value = "튜티 이메일")
  @NotEmpty(message = "튜티의 이메일는 반드시 있어야 합니다.")
  @Email(message = "유효하지 않은 이메일입니다.")
  private String tuteeEmail;

  @ApiModelProperty(value = "튜터/튜티 타입")
  private RoleType role;
}
