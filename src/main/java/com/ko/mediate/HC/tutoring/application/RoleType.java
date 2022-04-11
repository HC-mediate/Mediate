package com.ko.mediate.HC.tutoring.application;

import java.util.Arrays;
import java.util.Locale;

public enum RoleType {
  ROLE_TUTOR("ROLE_TUTOR"),
  ROLE_TUTEE("ROLE_TUTEE"),
  ROLE_USER("ROLE_USER");

  private final String roleString;

  RoleType(String roleString) {
    this.roleString = roleString.toUpperCase(Locale.ROOT);
  }

  public static RoleType fromString(String s) {
    return Arrays.stream(RoleType.values())
        .filter(v -> v.roleString.equals(s))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown Value: " + s));
  }
}
