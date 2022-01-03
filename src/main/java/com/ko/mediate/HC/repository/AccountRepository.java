package com.ko.mediate.HC.repository;

import com.ko.mediate.HC.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {}
