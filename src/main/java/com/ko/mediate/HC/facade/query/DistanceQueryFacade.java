package com.ko.mediate.HC.facade.query;

import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.tutee.application.TuteeQueryProcessor;
import com.ko.mediate.HC.tutee.application.response.GetTuteeListDto;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutor.application.TutorQueryProcessor;
import com.ko.mediate.HC.tutor.application.response.GetTutorListDto;
import com.ko.mediate.HC.tutor.domain.Tutor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DistanceQueryFacade {
  private final AccountService accountService;
  private final TutorQueryProcessor tutorQueryProcessor;
  private final TuteeQueryProcessor tuteeQueryProcessor;
  private final DistanceQueryRepository distanceQueryRepository;

  private Slice<Account> getAllAccountByDistance(
      Account account, PageRequest pageRequest, int radius) {
    return distanceQueryRepository.getAllAccountByDistance(
        pageRequest, new SearchCondition(account.getTutoringLocation(), radius));
  }

  public GetTutorListDto getAllTutorByDistance(
      UserInfo userInfo, PageRequest pageRequest, int radius) {
    Account account = accountService.getAccountByEmail(userInfo.getAccountEmail());
    Slice<Account> accounts = getAllAccountByDistance(account, pageRequest, radius);
    List<Tutor> contents =
        tutorQueryProcessor.getAllTutorsByAccountEmail(
            accounts.getContent().stream().map(Account::getEmail).collect(Collectors.toList()));
    return GetTutorListDto.fromEntities(new SliceImpl<>(contents, pageRequest, accounts.hasNext()));
  }

  public GetTuteeListDto getAllTuteeByDistance(
      UserInfo userInfo, PageRequest pageRequest, int radius) {
    Account account = accountService.getAccountByEmail(userInfo.getAccountEmail());
    Slice<Account> accounts = getAllAccountByDistance(account, pageRequest, radius);
    List<Tutee> contents =
        tuteeQueryProcessor.getAllTuteesByAccountEmails(
            accounts.getContent().stream().map(Account::getEmail).collect(Collectors.toList()));
    return GetTuteeListDto.fromEntites(new SliceImpl<>(contents, pageRequest, accounts.hasNext()));
  }
}
