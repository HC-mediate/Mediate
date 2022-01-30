package com.ko.mediate.HC.tutor.Infra;

import com.ko.mediate.HC.tutor.domain.Tutor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTutorRepository extends JpaRepository<Tutor, Long> {
  @Query("SELECT t FROM Tutor t WHERE t.accountId.accountId = :accountId")
  Optional<Tutor> findByAccountId(@Param("accountId") String accountId);

  @Query(
      "SELECT t, a FROM Tutor t, Account a WHERE t.accountId.accountId = :accountId and a.accountId.accountId = :accountId")
  List<Object[]> findTutorAccountInfoById(@Param("accountId") String accountId);
}
