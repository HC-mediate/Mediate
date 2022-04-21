package com.ko.mediate.HC.tutee.infra;

import com.ko.mediate.HC.tutee.domain.Tutee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTuteeRepository extends JpaRepository<Tutee, Long>, JpaTuteeCustomRepository {

  @Query("SELECT t FROM Tutee t WHERE t.accountId.accountId = :accountId")
  Optional<Tutee> findByAccountId(@Param("accountId") String accountId);

  @Query(
      "SELECT t, a FROM Tutee t, Account a WHERE t.accountId.accountId = :accountId and a.accountId.accountId = :accountId")
  List<Object[]> findTuteeAccountInfoById(@Param("accountId") String accountId);
}
