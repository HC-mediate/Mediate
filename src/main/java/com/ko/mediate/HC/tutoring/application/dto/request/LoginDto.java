package com.ko.mediate.HC.tutoring.application.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginDto {
  @NotEmpty(message = "아이디는 반드시 들어가야 합니다.")
  private String id;
  @NotEmpty(message = "비밀번호는 반드시 들어가야 합니다.")
  private String password;
}
