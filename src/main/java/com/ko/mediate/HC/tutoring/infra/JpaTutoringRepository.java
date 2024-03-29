package com.ko.mediate.HC.tutoring.infra;

import com.ko.mediate.HC.tutoring.domain.Tutoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaTutoringRepository
        extends JpaRepository<Tutoring, Long>, JpaTutoringCustomRepository {
    @Query(
            "SELECT t FROM Tutoring t WHERE t.tutorEmail = :accountEmail OR t.tuteeEmail = :accountEmail")
    List<Tutoring> findAllTutoringByAccountEmail(@Param("accountEmail") String accountEmail);

    @Query("SELECT t FROM Tutoring t LEFT JOIN FETCH t.progresses WHERE t.id = :tutoringId")
    Optional<Tutoring> findByTutoringIdWithDetail(@Param("tutoringId") Long tutoringId);
}
