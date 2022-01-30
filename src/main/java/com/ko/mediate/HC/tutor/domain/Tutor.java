package com.ko.mediate.HC.tutor.domain;

import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.auth.domain.AccountId;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
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
  private Long id;

  @Embedded
  private AccountId accountId;

  @Column(name = "address")
  private String address;

  @Column(name = "name")
  private String name;

  @Column(name = "curriculum")
  @Enumerated(value = EnumType.STRING)
  private Curriculum curriculum; // 교과 과정

  @Embedded private AcademicInfo academicInfo; // 튜터의 학생 정보

  protected Tutor() {};

  public Tutor(String accountId, String name, String address, Curriculum curriculum, AcademicInfo academicInfo) {
    this.accountId = new AccountId(accountId);
    this.name = name;
    this.address = address;
    this.curriculum = curriculum;
    this.academicInfo = academicInfo;
  }

  public String getStringAccountId() {
    return this.accountId.getAccountId();
  }
}
