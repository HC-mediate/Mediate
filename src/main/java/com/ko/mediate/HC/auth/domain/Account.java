package com.ko.mediate.HC.auth.domain;

import com.ko.mediate.HC.auth.exception.AccountPasswordNotEqualsException;
import com.ko.mediate.HC.tutoring.application.RoleType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tb_account")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "name")
  private String name;

  @Column(name = "is_activated")
  private Boolean isActivated;

  @Column(name = "authority")
  private String authority;

  @Column(name = "phone_num")
  private String phoneNum;

  @Transient
  private Set<RoleType> roles = new HashSet<>();

  protected Account() {}
  ;

  @Builder
  public Account(String email, String password, String name, String phoneNum, String authority) {
    this.email = email;
    this.password = password;
    this.authority = authority;
    this.name = name;
    this.isActivated = true;
    this.phoneNum = phoneNum;
  }

  public boolean isActivated() {
    return this.isActivated;
  }

  public void joinTutor() {
    this.authority = RoleType.ROLE_TUTOR.name();
  }

  public void joinTutee() {
    this.authority = RoleType.ROLE_TUTEE.name();
  }

  public void authenticate(String encodePassword){
    if(!password.equals(encodePassword)){
      throw new AccountPasswordNotEqualsException();
    }
  }
}
