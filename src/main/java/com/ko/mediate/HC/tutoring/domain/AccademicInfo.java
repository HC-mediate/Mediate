package com.ko.mediate.HC.tutoring.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class AccademicInfo {
  @Column(name = "school")
  private String school;

  @Column(name = "major", nullable = true)
  private String major;

  @Column(name = "grade")
  private String grade;

  public AccademicInfo(String school, String grade) {
    this.school = school;
    this.grade = grade;
  }

  protected AccademicInfo() {};

  public AccademicInfo(String school, String major, String grade) {
    this.school = school;
    this.major = major;
    this.grade = grade;
  }
}
