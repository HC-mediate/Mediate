package com.ko.mediate.HC.tutoring.infra;

import com.ko.mediate.HC.tutoring.domain.Tutoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTutoringRepository extends JpaRepository<Tutoring, Long> {
  Tutoring Save(Tutoring tutoring);
}
