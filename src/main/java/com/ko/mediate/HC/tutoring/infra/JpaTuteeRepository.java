package com.ko.mediate.HC.tutoring.infra;

import com.ko.mediate.HC.tutoring.domain.Tutee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTuteeRepository extends JpaRepository<Tutee, Long> {

  @Query("SELECT t FROM Tutee t WHERE t.accountId = :accountId")
  Optional<Tutee> findByAccountId(@Param("accountId") String accountId);
}
