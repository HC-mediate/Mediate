package com.ko.mediate.HC.account.application.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupDto {
  @NotEmpty
  @Size(min = 3, max = 100, message = "아이디는 최소 3자이상, 100자이하여야 합니다.")
  private String id;
  @NotEmpty
  @Size(min = 3, max = 100, message = "비밀번호는 최소 3자이상, 100자이하여야 합니다.")
  private String password;
  @NotEmpty
  @Pattern(regexp="(^$|[0-9]{10})", message = "유효하지 않은 전화번호입니다.")
  private String phoneNum;
}
