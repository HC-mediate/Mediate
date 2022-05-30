package com.ko.mediate.HC.tutor.infra;

import com.ko.mediate.HC.common.domain.DistanceCondition;
import com.ko.mediate.HC.tutor.domain.Tutor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface JpaTutorCustomRepository {
  Slice<Tutor> findAllTutorOrderByDistance(Pageable Pageable, DistanceCondition condition);
}
