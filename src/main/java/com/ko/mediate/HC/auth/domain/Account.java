package com.ko.mediate.HC.auth.domain;

import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.tutoring.application.RoleType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
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
  @Getter(AccessLevel.PROTECTED)
  private String role;

  @Column(name = "phone_num")
  private String phoneNum;

  @Embedded private ProfileImage profileImage;

  @Transient List<RoleType> roles = new ArrayList<>();

  protected Account() {}
  ;

  @PrePersist
  void enumToString() {
    this.role =
        String.join(",", roles.stream().map(r -> r.toString()).collect(Collectors.toList()));
  }

  @PostLoad
  void stringToEnum() {
    this.roles =
        Arrays.stream(role.split(",")).map(RoleType::fromString).collect(Collectors.toList());
  }

  @Builder
  public Account(
      String email,
      String password,
      String name,
      String phoneNum,
      String authority,
      String profileKey,
      String profileUrl) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.isActivated = true;
    this.phoneNum = phoneNum;
    this.profileImage = new ProfileImage(profileKey, profileUrl);
    join(authority);
  }

  @Builder
  public Account(
      String email,
      String password,
      String name,
      String phoneNum,
      RoleType role,
      String profileKey,
      String profileUrl) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.isActivated = true;
    this.phoneNum = phoneNum;
    this.profileImage = new ProfileImage(profileKey, profileUrl);
    join(role);
  }

  public void changeProfileImage(String profileKey, String profileUrl) {
    this.profileImage = new ProfileImage(profileKey, profileUrl);
  }

  public boolean isDeactivated() {
    return !this.isActivated;
  }

  public boolean hasRole(RoleType role) {
    return roles.contains(role);
  }

  public void joinTutor() {
    join(RoleType.ROLE_TUTOR);
  }

  public void joinTutee() {
    join(RoleType.ROLE_TUTEE);
  }

  private boolean join(String roleType) {
    if (roles.contains(RoleType.fromString(roleType))) {
      throw new MediateIllegalStateException(String.format("이미 %s로 가입된 회원입니다.", roleType));
    }
    roles.add(RoleType.fromString(roleType));
    return true;
  }

  private boolean join(RoleType roleType) {
    if (roles.contains(roleType)) {
      throw new MediateIllegalStateException(
          String.format("이미 %s로 가입된 회원입니다.", roleType.toString()));
    }
    roles.add(roleType);
    return true;
  }
}
