package com.ko.mediate.HC.auth.application.response;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutee.application.response.GetTuteeDto;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutor.application.response.GetTutorDto;
import com.ko.mediate.HC.tutor.domain.Tutor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GetAccountFacadeInfoDto {
  private GetAccountInfoDto accountInfo;
  private GetTutorDto tutorInfo;
  private GetTuteeDto tuteeInfo;

  public static GetAccountFacadeInfoDto fromEntities(Account account, Tutor tutor, Tutee tutee) {
    return GetAccountFacadeInfoDto.builder()
        .accountInfo(GetAccountInfoDto.fromEntity(account))
        .tutorInfo(GetTutorDto.fromEntity(tutor))
        .tuteeInfo(GetTuteeDto.fromEntity(tutee))
        .build();
  }
}
