package com.ko.mediate.HC.account.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "tb_account")
@Getter
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;

  @Column(unique = true)
  private String id;

  private String password;
  private String phoneNum;
  private Boolean isActivated;

  @ElementCollection private Set<String> Authority = new HashSet<>();

  protected Account() {}
  ;

  public Account(String id, String password, String phoneNum, Set<String> authority) {
    this.id = id;
    this.password = password;
    this.phoneNum = phoneNum;
    this.isActivated = true;
    Authority = authority;
  }

  public boolean isActivated() {
    return this.isActivated;
  }
}
