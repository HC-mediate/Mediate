package com.ko.mediate.HC.auth.application.dto.response;

import com.ko.mediate.HC.auth.domain.Account;
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

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "전화번호")
    private String phoneNum;

    @ApiModelProperty(value = "프로필 사진 경로")
    private String profileUrl;

    @Builder
    private GetAccountInfoDto(
            String email,
            String name,
            String nickname,
            String phoneNum,
            String profileUrl) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.profileUrl = profileUrl;
    }

    public static GetAccountInfoDto fromEntity(Account account) {
        return GetAccountInfoDto.builder()
                .email(account.getEmail())
                .name(account.getName())
                .nickname(account.getNickname())
                .phoneNum(account.getPhoneNum())
                .profileUrl(account.getProfileImage().getProfileUrl())
                .build();
    }
}
