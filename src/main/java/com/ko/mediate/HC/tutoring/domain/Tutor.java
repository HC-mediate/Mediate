package com.ko.mediate.HC.tutoring.domain;

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
  private Long idx;

  @Embedded
  private AccountId accountId;

  @Column(name = "address")
  private String address;

  @Column(name = "name")
  private String name;

  @Column(name = "curriculum")
  @Enumerated(value = EnumType.STRING)
  private Curriculum curriculum; // 교과 과정

  @Embedded private AccademicInfo accademicInfo; // 튜터의 학생 정보

  protected Tutor() {};

  public Tutor(String accountId, String name, String address, Curriculum curriculum, AccademicInfo accademicInfo) {
    this.accountId = new AccountId(accountId);
    this.name = name;
    this.address = address;
    this.curriculum = curriculum;
    this.accademicInfo = accademicInfo;
  }

  public String getStringAccountId() {
    return this.accountId.getAccountId();
  }
}
