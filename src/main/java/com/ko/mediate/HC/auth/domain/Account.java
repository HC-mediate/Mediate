package com.ko.mediate.HC.auth.domain;

import com.ko.mediate.HC.tutoring.application.RoleType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "tb_account")
@DynamicUpdate
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

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private RoleType role;

  @Column(name = "phone_num")
  private String phoneNum;

  @Column(name = "profile_url")
  private String profileUrl;

  protected Account() {}
  ;

  @Builder
  public Account(
      String email,
      String password,
      String name,
      String phoneNum,
      String authority,
      String profileUrl) {
    this.email = email;
    this.password = password;
    this.role = RoleType.fromString(authority);
    this.name = name;
    this.isActivated = true;
    this.phoneNum = phoneNum;
    this.profileUrl = profileUrl;
  }

  @Builder
  public Account(
      String email,
      String password,
      String name,
      String phoneNum,
      RoleType role,
      String profileUrl) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.name = name;
    this.isActivated = true;
    this.phoneNum = phoneNum;
    this.profileUrl = profileUrl;
  }

  public void changeProfileImage(String profileUrl) {
    this.profileUrl = profileUrl;
  }

  public boolean isActivated() {
    return this.isActivated;
  }

  public void joinTutor() {
    this.role = RoleType.ROLE_TUTOR;
  }

  public void joinTutee() {
    this.role = RoleType.ROLE_TUTEE;
  }
}
