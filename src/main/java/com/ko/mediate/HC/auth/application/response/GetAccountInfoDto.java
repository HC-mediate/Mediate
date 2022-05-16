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
  @ApiModelProperty(value = "계정 ID")
  private String accountId;

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

  @Builder
  private GetAccountInfoDto(
      String accountId,
      String name,
      String phoneNum,
      String schoolName,
      String grade,
      String major,
      RoleType type) {
    this.accountId = accountId;
    this.name = name;
    this.phoneNum = phoneNum;
    this.schoolName = schoolName;
    this.grade = grade;
    this.major = major;
    this.type = type;
  }

  public static GetAccountInfoDto fromEntity(Account account) {
    return GetAccountInfoDto.builder()
        .accountId(account.getStringAccountId())
        .name(account.getName())
        .phoneNum(account.getPhoneNum())
        .type(RoleType.ROLE_USER)
        .build();
  }

  public static GetAccountInfoDto fromEntity(Account account, Tutor tutor) {
    return GetAccountInfoDto.builder()
        .accountId(tutor.getAccountId().getAccountId())
        .name(tutor.getName())
        .schoolName(tutor.getAcademicInfo().getSchool())
        .major(tutor.getAcademicInfo().getMajor())
        .grade(tutor.getAcademicInfo().getGrade())
        .type(RoleType.ROLE_TUTOR)
        .phoneNum(account.getPhoneNum())
        .build();
  }

  public static GetAccountInfoDto fromEntity(Account account, Tutee tutee) {
    return GetAccountInfoDto.builder()
        .accountId(tutee.getAccountId().getAccountId())
        .name(tutee.getName())
        .schoolName(tutee.getAcademicInfo().getSchool())
        .major(tutee.getAcademicInfo().getMajor())
        .grade(tutee.getAcademicInfo().getGrade())
        .type(RoleType.ROLE_TUTEE)
        .phoneNum(account.getPhoneNum())
        .build();
  }
}
