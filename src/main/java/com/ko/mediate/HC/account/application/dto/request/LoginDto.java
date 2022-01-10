package com.ko.mediate.HC.account.application.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginDto {
  @NotEmpty
  private String id;
  @NotEmpty
  private String password;
}
