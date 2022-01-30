package com.ko.mediate.HC.auth.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class AccountId {
  @Column(name = "account_id")
  private String accountId;

  protected AccountId() {};

  public AccountId(String accountId) {
    this.accountId = accountId;
  }
}
