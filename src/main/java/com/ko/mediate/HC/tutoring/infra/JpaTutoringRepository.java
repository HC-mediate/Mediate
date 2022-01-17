package com.ko.mediate.HC.tutoring.infra;

import com.ko.mediate.HC.tutoring.domain.Tutoring;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTutoringRepository extends JpaRepository<Tutoring, Long> {
  @Query(
      "SELECT t FROM Tutoring t WHERE t.id = :tutoringId AND (t.tutorId.accountId = :accountId OR t.tuteeId.accountId = :accountId)")
  Optional<Tutoring> findByIdWithAccountId(
      @Param("tutoringId") long tutoringId, @Param("accountId") String accountId);
}
