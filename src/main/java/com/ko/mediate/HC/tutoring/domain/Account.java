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
@Table(name = "tb_account")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  @Column(unique = true)
  private AccountId accountId;

  @Column(name = "account_pw")
  private String password;

  @Column(name = "is_activated")
  private Boolean isActivated;

  @Column(name = "authority")
  private String authority;

  protected Account() {};

  public Account(String accountId, String password, String authority) {
    this.accountId = new AccountId(accountId);
    this.password = password;
    this.authority = authority;
    this.isActivated = true;
  }

  public boolean isActivated() {
    return this.isActivated;
  }

  public String getStringAccountId(){
    return this.accountId.getAccountId();
  }
}