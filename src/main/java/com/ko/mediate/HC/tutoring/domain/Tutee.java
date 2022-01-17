package com.ko.mediate.HC.tutoring.domain;

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
  private AccademicInfo accademicInfo;

  @Embedded
  private AccountId accountId;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;

  protected Tutee() {};

  public Tutee(String accountId, String name, String address, AccademicInfo accademicInfo) {
    this.accountId = new AccountId(accountId);
    this.name = name;
    this.accademicInfo = accademicInfo;
    this.address = address;
  }

  public String getStringAccountId(){
    return this.accountId.getAccountId();
  }
}
