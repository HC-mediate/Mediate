package com.ko.mediate.HC.facade.query;

import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.application.dto.response.GetAccountFacadeInfoDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.tutee.application.TuteeQueryProcessor;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutor.application.TutorQueryProcessor;
import com.ko.mediate.HC.tutor.domain.Tutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountQueryFacade {
    private final AccountService accountService;
    private final TutorQueryProcessor tutorQueryProcessor;
    private final TuteeQueryProcessor tuteeQueryProcessor;

    public GetAccountFacadeInfoDto getAccountInfo(UserInfo userInfo) {
        Account account = accountService.getAccountByEmail(userInfo.getAccountEmail());
        Tutor tutor = tutorQueryProcessor.getTutorByAccountEmail(userInfo.getAccountEmail());
        Tutee tutee = tuteeQueryProcessor.getTuteeByAccountEmail(userInfo.getAccountEmail());
        return GetAccountFacadeInfoDto.fromEntities(account, tutor, tutee);
    }
}
