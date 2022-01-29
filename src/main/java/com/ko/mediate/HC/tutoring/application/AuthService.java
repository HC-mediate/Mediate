package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.tutoring.application.dto.response.GetAccountDto;
import com.ko.mediate.HC.tutoring.domain.Account;
import com.ko.mediate.HC.tutoring.domain.AccountId;
import com.ko.mediate.HC.tutoring.infra.JpaAccountRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
  private final JpaAccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String accountId) throws AuthenticationException {
    return accountRepository
        .findByAccountId(new AccountId(accountId))
        .map(user -> createUser(accountId, user))
        .orElseThrow(() -> new BadCredentialsException("등록된 아이디가 없습니다."));
  }

  private User createUser(String accountId, Account account) {
    if (!account.isActivated()) {
      throw new RuntimeException("활성화되지 않은 아이디입니다.");
    }
    List<GrantedAuthority> grantedAuthorities =
        Arrays.asList(new SimpleGrantedAuthority(account.getAuthority()));
    return new User(account.getStringAccountId(), account.getPassword(), grantedAuthorities);
  }

  public GetAccountDto getAccountDetail(String accountId) {
    return accountRepository
        .findByAccountId(new AccountId(accountId))
        .map(
            a -> {
              return new GetAccountDto(a.getStringAccountId(), a.getPhoneNum());
            })
        .orElseThrow(() -> new IllegalArgumentException("요청한 계정 ID가 없습니다."));
  }
}
