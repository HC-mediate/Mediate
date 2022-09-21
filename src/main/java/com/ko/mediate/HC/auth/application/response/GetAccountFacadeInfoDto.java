package com.ko.mediate.HC.auth.application.response;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutee.application.response.GetTuteeDto;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutor.application.response.GetTutorDto;
import com.ko.mediate.HC.tutor.domain.Tutor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetAccountFacadeInfoDto {
    private GetAccountInfoDto accountInfo;
    private GetTutorDto tutorInfo;
    private GetTuteeDto tuteeInfo;

    @Builder
    private GetAccountFacadeInfoDto(
            GetAccountInfoDto accountInfo, GetTutorDto tutorInfo, GetTuteeDto tuteeInfo) {
        this.accountInfo = accountInfo;
        this.tutorInfo = tutorInfo;
        this.tuteeInfo = tuteeInfo;
    }

    public static GetAccountFacadeInfoDto fromEntities(Account account, Tutor tutor, Tutee tutee) {
        return GetAccountFacadeInfoDto.builder()
                .accountInfo(GetAccountInfoDto.fromEntity(account))
                .tutorInfo(Objects.isNull(tutor) ? null : GetTutorDto.fromEntity(tutor))
                .tuteeInfo(Objects.isNull(tutee) ? null : GetTuteeDto.fromEntity(tutee))
                .build();
    }
}
