package com.ko.mediate.HC.auth.resolver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfo {
  private Long accountId;
  private String accountEmail;
  private String authority;
}
