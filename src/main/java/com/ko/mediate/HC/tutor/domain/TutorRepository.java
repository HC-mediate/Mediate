package com.ko.mediate.HC.tutor.domain;

import java.util.List;
import java.util.Optional;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TutorRepository {
  Tutor save(Tutor tutor);

  Page<Tutor> findAll(Pageable pageable);

  Optional<Tutor> findByAccountId(String accountId);

  List<Object[]> findTutorAccountInfoById(String accountId);

  List<Tutor> findTutorOrderByDistance(
      Point location, Integer radius, Integer startAt, Integer size);
}
