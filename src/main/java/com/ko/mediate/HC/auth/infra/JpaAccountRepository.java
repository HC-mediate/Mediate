package com.ko.mediate.HC.auth.infra;

import com.ko.mediate.HC.auth.domain.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccountRepository extends JpaRepository<Account, Long> {
  boolean existsByEmail(String email);

  @Query("SELECT a FROM Account a WHERE a.email = :email")
  Optional<Account> findAccountByEmail(@Param("email") String email);
}
