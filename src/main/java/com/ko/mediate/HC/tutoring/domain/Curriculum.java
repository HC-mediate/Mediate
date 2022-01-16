package com.ko.mediate.HC.tutoring.domain;

public enum Curriculum {
  HIGH("고등학교"),
  MIDDLE("중학교"),
  ELEMENT("초등학교");
  private String curriculumName;

  public String getCurriculumName() {
    return curriculumName;
  }

  Curriculum(String curriculumName) {
    this.curriculumName = curriculumName;
  }
}
