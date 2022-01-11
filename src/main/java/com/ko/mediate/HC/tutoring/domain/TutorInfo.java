package com.ko.mediate.HC.tutoring.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class TutorInfo {
  @Column(name = "school")
  private String school;

  @Column(name = "major")
  private String major;

  @Column(name = "grade")
  private String grade;

  protected TutorInfo() {};

  public TutorInfo(String school, String major, String grade) {
    this.school = school;
    this.major = major;
    this.grade = grade;
  }
}
