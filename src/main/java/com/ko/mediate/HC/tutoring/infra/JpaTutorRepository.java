package com.ko.mediate.HC.tutoring.infra;

import com.ko.mediate.HC.tutoring.domain.Tutor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTutorRepository extends JpaRepository<Tutor, Long> {
  @Query("SELECT t FROM Tutor t WHERE t.accountId = :accountId")
  Optional<Tutor> findByAccountId(@Param("accountId") String accountId);
}
