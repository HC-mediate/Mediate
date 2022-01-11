package com.ko.mediate.HC.tutoring.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class TuteeInfo {
  @Column(name = "school")
  private String school;
  @Column(name = "grade")
  private String grade;

  public TuteeInfo(String school, String grade) {
    this.school = school;
    this.grade = grade;
  }

  protected TuteeInfo(){};
}
