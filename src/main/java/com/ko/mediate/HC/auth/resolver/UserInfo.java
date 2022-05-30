package com.ko.mediate.HC.auth.resolver;

import com.ko.mediate.HC.tutoring.application.RoleType;
import io.jsonwebtoken.Claims;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfo {
  private Long accountId;
  private String accountEmail;
  private RoleType role;
}
