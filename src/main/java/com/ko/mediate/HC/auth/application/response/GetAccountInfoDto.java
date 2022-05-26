package com.ko.mediate.HC.auth.application.response;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutoring.application.RoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetAccountInfoDto {
  @ApiModelProperty(value = "계정 이메일")
  private String email;

  @ApiModelProperty(value = "이름")
  private String name;

  @ApiModelProperty(value = "전화번호")
  private String phoneNum;

  @ApiModelProperty(value = "학교 이름")
  private String schoolName;

  @ApiModelProperty(value = "학년")
  private String grade;

  @ApiModelProperty(value = "학과")
  private String major;

  @ApiModelProperty(value = "튜터/튜티 타입")
  private RoleType type;

  @ApiModelProperty(value = "프로필 사진 경로")
  private String profileUrl;

  @Builder
  private GetAccountInfoDto(
      String email,
      String name,
      String phoneNum,
      String schoolName,
      String grade,
      String major,
      String profileUrl,
      RoleType type) {
    this.email = email;
    this.name = name;
    this.phoneNum = phoneNum;
    this.schoolName = schoolName;
    this.grade = grade;
    this.major = major;
    this.profileUrl = profileUrl;
    this.type = type;
  }

  public static GetAccountInfoDto fromEntity(Account account) {
    return GetAccountInfoDto.builder()
        .email(account.getEmail())
        .name(account.getName())
        .phoneNum(account.getPhoneNum())
        .type(RoleType.ROLE_USER)
        .profileUrl(account.getProfileUrl())
        .build();
  }

  public static GetAccountInfoDto fromEntity(Tutor tutor) {
    return GetAccountInfoDto.builder()
        .email(tutor.getAccount().getEmail())
        .name(tutor.getAccount().getName())
        .schoolName(tutor.getAcademicInfo().getSchool())
        .major(tutor.getAcademicInfo().getMajor())
        .grade(tutor.getAcademicInfo().getGrade())
        .type(RoleType.ROLE_TUTOR)
        .phoneNum(tutor.getAccount().getPhoneNum())
        .profileUrl(tutor.getAccount().getProfileUrl())
        .build();
  }

  public static GetAccountInfoDto fromEntity(Tutee tutee) {
    return GetAccountInfoDto.builder()
        .email(tutee.getAccount().getEmail())
        .name(tutee.getAccount().getName())
        .schoolName(tutee.getAcademicInfo().getSchool())
        .major(tutee.getAcademicInfo().getMajor())
        .grade(tutee.getAcademicInfo().getGrade())
        .type(RoleType.ROLE_TUTEE)
        .phoneNum(tutee.getAccount().getPhoneNum())
        .profileUrl(tutee.getAccount().getProfileUrl())
        .build();
  }
}
