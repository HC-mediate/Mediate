package com.ko.mediate.HC.facade;

import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.application.dto.response.GetAccountFacadeInfoDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.facade.query.AccountQueryFacade;
import com.ko.mediate.HC.tutee.application.TuteeQueryProcessor;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutor.application.TutorQueryProcessor;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountQueryFacade 테스트")
class AccountQueryFacadeTest {
    @Mock
    AccountService accountService;
    @Mock
    TutorQueryProcessor tutorQueryProcessor;
    @Mock
    TuteeQueryProcessor tuteeQueryProcessor;

    AccountQueryFacade queryFacade;

    static final String accountEmail = "test@naver.com";
    static final String accountName = "test_naver";
    static final String accountNickname = "test_nickname";
    static final String tutorSchool = "tutor-school";
    static final String tuteeSchool = "tutee-school";
    Account account;
    Tutor tutor;
    Tutee tutee;

    @BeforeEach
    void beforeEach() {
        account = Account.builder().email(accountEmail).name(accountName).password("1234").phoneNum("010-1234-5678").build();
        tutor = Tutor.builder().account(account).school(tutorSchool).curriculums(Arrays.stream(Curriculum.values()).collect(Collectors.toList())).build();
        tutee = Tutee.builder().account(account).school(tuteeSchool).build();
        queryFacade = new AccountQueryFacade(accountService, tutorQueryProcessor, tuteeQueryProcessor);
    }

    @DisplayName("튜터, 튜티가 아닌 유저는 계정 정보만 조회")
    @Test
    void onlyAccountInfoTest() {
        // given
        given(accountService.getAccountByEmail(accountEmail)).willReturn(account);
        given(tutorQueryProcessor.getTutorByAccountEmail(accountEmail)).willReturn(null);
        given(tuteeQueryProcessor.getTuteeByAccountEmail(accountEmail)).willReturn(null);

        // when
        GetAccountFacadeInfoDto result = queryFacade.getAccountInfo(UserInfo.builder().accountId(1L)
                .accountEmail(accountEmail)
                .accountNickname(accountNickname)
                .roles(RoleType.ROLE_USER.name()).build());

        // then
        assertThat(result.getAccountInfo().getEmail()).isEqualTo(accountEmail);
        assertThat(result.getAccountInfo().getName()).isEqualTo(accountName);
        assertThat(result.getTuteeInfo()).isNull();
        assertThat(result.getTutorInfo()).isNull();
    }

    @DisplayName("계정, 튜터의 정보만 조회")
    @Test
    void tutorInfoTest() {
        // given
        given(accountService.getAccountByEmail(accountEmail)).willReturn(account);
        given(tutorQueryProcessor.getTutorByAccountEmail(accountEmail)).willReturn(tutor);
        given(tuteeQueryProcessor.getTuteeByAccountEmail(accountEmail)).willReturn(null);

        // when
        GetAccountFacadeInfoDto result = queryFacade.getAccountInfo(UserInfo.builder().accountId(1L)
                .accountEmail(accountEmail)
                .accountNickname(accountNickname)
                .roles(RoleType.ROLE_USER.name()).build());

        // then
        assertThat(result.getAccountInfo().getName()).isEqualTo(accountName);
        assertThat(result.getTutorInfo().getSchool()).isEqualTo(tutorSchool);
        assertThat(result.getTuteeInfo()).isNull();
    }

    @DisplayName("모든 정보 조회")
    @Test
    void accountAllInfoTest() {
        // given
        given(accountService.getAccountByEmail(accountEmail)).willReturn(account);
        given(tutorQueryProcessor.getTutorByAccountEmail(accountEmail)).willReturn(tutor);
        given(tuteeQueryProcessor.getTuteeByAccountEmail(accountEmail)).willReturn(tutee);

        // when
        GetAccountFacadeInfoDto result = queryFacade.getAccountInfo(UserInfo.builder().accountId(1L)
                .accountEmail(accountEmail)
                .accountNickname(accountNickname)
                .roles(RoleType.ROLE_USER.name()).build());

        // then
        assertThat(result.getAccountInfo().getName()).isEqualTo(accountName);
        assertThat(result.getTutorInfo().getCurriculums()).contains(Curriculum.values());
        assertThat(result.getTutorInfo().getSchool()).isEqualTo(tutorSchool);
        assertThat(result.getTuteeInfo().getSchool()).isEqualTo(tuteeSchool);
    }
}
