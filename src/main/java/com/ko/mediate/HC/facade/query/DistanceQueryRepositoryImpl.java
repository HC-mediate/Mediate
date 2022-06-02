package com.ko.mediate.HC.facade.query;

import static com.ko.mediate.HC.auth.domain.QAccount.account;

import com.ko.mediate.HC.auth.domain.Account;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DistanceQueryRepositoryImpl implements DistanceQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Slice<Account> getAllAccountByDistance(Pageable pageable, SearchCondition condition) {
    List<Account> contents =
        queryFactory
            .selectFrom(account)
            .where(
                account.isActivated.isTrue(),
                nearByAccount(condition).loe(condition.getRadius() * 1000))
            .orderBy(nearByAccount(condition).asc(), account.name.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .fetch();
    boolean hasNext = false;
    if (contents.size() > pageable.getPageSize()) {
      contents.remove(pageable.getPageSize());
      hasNext = true;
    }
    return new SliceImpl<>(contents, pageable, hasNext);
  }

  private NumberTemplate<Double> nearByAccount(SearchCondition condition) {
    return Expressions.numberTemplate(
        Double.class,
        "function('ST_Distance_Sphere', {0}, POINT({1}, {2}))",
        account.tutoringLocation,
        condition.getLocation().getLatitude(),
        condition.getLocation().getLongitude());
  }
}
