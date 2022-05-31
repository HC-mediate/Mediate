package com.ko.mediate.HC.facade.query;

import com.ko.mediate.HC.auth.domain.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface DistanceQueryRepository {
  Slice<Account> getAllAccountByDistance(Pageable pageable, SearchCondition condition);
}
