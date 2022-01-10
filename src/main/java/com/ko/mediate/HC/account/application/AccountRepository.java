package com.ko.mediate.HC.account.application;

import com.ko.mediate.HC.account.domain.Account;
import java.util.Optional;

public interface AccountRepository {
  Optional<Account> findByAccountId(String accountId);

  Account save(Account account);
}
