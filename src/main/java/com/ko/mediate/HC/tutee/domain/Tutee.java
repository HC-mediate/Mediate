package com.ko.mediate.HC.tutee.domain;

import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutoring.domain.AccountId;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tb_tutee")
public class Tutee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private AcademicInfo academicInfo;

  @Embedded
  private AccountId accountId;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;

  protected Tutee() {};

  public Tutee(String accountId, String name, String address, AcademicInfo academicInfo) {
    this.accountId = new AccountId(accountId);
    this.name = name;
    this.academicInfo = academicInfo;
    this.address = address;
  }

  public String getStringAccountId(){
    return this.accountId.getAccountId();
  }
}
