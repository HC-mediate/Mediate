package com.ko.mediate.HC.account.application;

import com.ko.mediate.HC.account.domain.Account;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
@RequiredArgsConstructor
public class AccountLoginService implements UserDetailsService {
  private final AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
    return accountRepository.findByAccountId(accountId)
        .map(user -> createUser(accountId, user))
        .orElseThrow(() -> new UsernameNotFoundException(accountId + " -> 찾을 수 없음"));
  }

  private User createUser(String accountId, Account account){
    if(!account.isActivated()){
      throw new RuntimeException(accountId + "-> 활성화 X");
    }
    List<GrantedAuthority> grantedAuthorities = account.getAuthority().stream()
        .map(authority -> new SimpleGrantedAuthority(authority))
        .collect(Collectors.toList());
    return new User(account.getId(),
        account.getPassword(),
        grantedAuthorities);
  }
}
