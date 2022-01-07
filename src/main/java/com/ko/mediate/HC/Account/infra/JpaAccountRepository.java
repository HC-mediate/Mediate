package com.ko.mediate.HC.Account.infra;

import com.ko.mediate.HC.Account.application.AccountRepository;
import com.ko.mediate.HC.Account.domain.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {
  Optional<Account> findByAccountId(String accountId);

  Account save(Account account);
}
