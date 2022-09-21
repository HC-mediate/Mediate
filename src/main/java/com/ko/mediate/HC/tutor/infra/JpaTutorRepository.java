package com.ko.mediate.HC.tutor.infra;

import com.ko.mediate.HC.tutor.domain.Tutor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTutorRepository extends JpaRepository<Tutor, Long> {
    @Query("SELECT t FROM Tutor t JOIN FETCH t.account WHERE t.account.email = :accountEmail")
    Optional<Tutor> findTutorByAccountEmail(@Param("accountEmail") String email);

    @Query("SELECT t FROM Tutor t JOIN FETCH t.account a WHERE t.id = :tutorId")
    Optional<Tutor> findByIdFetchAccount(@Param("tutorId") Long tutorId);

    @Query("SELECT t FROM Tutor t JOIN FETCH t.account a WHERE a.email IN :accountEmails")
    List<Tutor> findAllByAccountEmails(@Param("accountEmails") List<String> emails);
}
