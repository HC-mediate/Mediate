package com.ko.mediate.HC.tutee.infra;

import com.ko.mediate.HC.common.domain.DistanceCondition;
import com.ko.mediate.HC.tutee.domain.Tutee;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface JpaTuteeCustomRepository {
  List<Tutee> findTutorOrderByDistance(PageRequest pageRequest, DistanceCondition condition);
}
