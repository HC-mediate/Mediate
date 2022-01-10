package com.ko.mediate.HC.Account.infra;

import com.ko.mediate.HC.Account.application.AccountRepository;
import com.ko.mediate.HC.Account.domain.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {
  @Query("SELECT a FROM Account a WHERE a.id = :accountId")
  Optional<Account> findByAccountId(@Param("accountId") String accountId);

  Account save(Account account);
}
