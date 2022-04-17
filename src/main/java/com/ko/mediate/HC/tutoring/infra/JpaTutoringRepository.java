package com.ko.mediate.HC.tutoring.infra;

import com.ko.mediate.HC.tutoring.domain.Tutoring;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTutoringRepository
    extends JpaRepository<Tutoring, Long>, JpaTutoringCustomRepository {
  @Query(
      "SELECT t FROM Tutoring t WHERE t.tutorId.accountId = :accountId OR t.tuteeId.accountId = :accountId")
  List<Tutoring> findAllTutoringByAccountId(@Param("accountId") String accountId);

  @Query("SELECT t FROM Tutoring t LEFT JOIN FETCH t.progresses WHERE t.id = :tutoringId")
  Optional<Tutoring> findByTutoringIdWithDetail(@Param("tutoringId") Long tutoringId);
}
