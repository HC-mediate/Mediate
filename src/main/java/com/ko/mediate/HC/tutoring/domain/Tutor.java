package com.ko.mediate.HC.tutoring.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tb_tutor")
public class Tutor {
  @Id
  @GeneratedValue
  private Long idx;

  @Embedded
  private AccountId accountId;

  @Column(name = "address")
  private String address;

  @Column(name = "curriculum")
  @Enumerated
  private Curricolum curriculum; // 교과 과정

  @Embedded
  private TutorInfo tutorInfo;

  @Embedded
  private TutorInfo studentInfo; // 학생 정보

  protected Tutor() {};
}
