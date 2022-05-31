package com.ko.mediate.HC.facade.query;

import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.tutee.application.response.GetTuteeListDto;
import com.ko.mediate.HC.tutor.application.response.GetTutorListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DistanceQueryFacade {
  private final AccountService accountService;
  private final DistanceQueryRepository distanceQueryRepository;

  //todo: 계정 Unique key로 튜터, 튜티 정보 목록 조회
  private Slice<Account> getAllAccountByDistance(
      Account account, PageRequest pageRequest, int radius) {
    return distanceQueryRepository.getAllAccountByDistance(
        pageRequest, new SearchCondition(account.getTutoringLocation(), radius));
  }

  public GetTutorListDto getAllTutorByDistance(
      UserInfo userInfo, PageRequest pageRequest, int radius) {
    Account account = accountService.getAccountByEmail(userInfo.getAccountEmail());
    Slice<Account> accounts = getAllAccountByDistance(account, pageRequest, radius);
    return null;
  }

  public GetTuteeListDto getAllTuteeByDistance(
      UserInfo userInfo, PageRequest pageRequest, int radius) {
    Account account = accountService.getAccountByEmail(userInfo.getAccountEmail());
    Slice<Account> accounts = getAllAccountByDistance(account, pageRequest, radius);
    return null;
  }
}
