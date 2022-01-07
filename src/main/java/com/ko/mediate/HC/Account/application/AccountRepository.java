package com.ko.mediate.HC.Account.application;

import com.ko.mediate.HC.Account.domain.Account;
import java.util.Optional;

public interface AccountRepository {
  Optional<Account> findByAccountId(String accountId);

  Account save(Account account);
}
