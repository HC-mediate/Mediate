package com.ko.mediate.HC.tutor.Infra;

import com.ko.mediate.HC.common.domain.DistanceCondition;
import com.ko.mediate.HC.tutor.domain.Tutor;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface JpaTutorCustomRepository {
  List<Tutor> findTutorOrderByDistance(PageRequest pageRequest, DistanceCondition condition);
}
