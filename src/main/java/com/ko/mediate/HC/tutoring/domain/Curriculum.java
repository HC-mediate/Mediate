package com.ko.mediate.HC.tutoring.domain;

import java.util.Arrays;

public enum Curriculum {
  HIGH("고등학교"),
  MIDDLE("중학교"),
  ELEMENT("초등학교");
  private String curriculumName;

  Curriculum(String curriculumName) {
    this.curriculumName = curriculumName;
  }

  public static Curriculum fromString(String s) {
    return Arrays.stream(Curriculum.values())
        .filter(curriculum -> curriculum.equals(s))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown Value: " + s));
  }
}
