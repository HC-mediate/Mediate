package com.ko.mediate.HC.homework.infra;

import com.ko.mediate.HC.homework.domain.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaHomeworkRepository extends JpaRepository<Homework, Long> {
    @Query("SELECT h FROM Homework h WHERE h.tuteeId = :tuteeId")
    List<Homework> findAllByTuteeId(@Param("tuteeId") String tuteeId);

    @Query("SELECT h FROM Homework h WHERE h.tutorId = :tutorId")
    List<Homework> findAllByTutorId(@Param("tutorId") String tutorId);
}
