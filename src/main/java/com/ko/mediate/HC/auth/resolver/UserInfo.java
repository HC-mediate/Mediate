package com.ko.mediate.HC.auth.resolver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfo {
  private String accountId;
  private String authority;
}
