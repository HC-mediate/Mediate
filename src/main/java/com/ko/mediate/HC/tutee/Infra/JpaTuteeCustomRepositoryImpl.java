package com.ko.mediate.HC.tutee.Infra;

import com.ko.mediate.HC.common.domain.DistanceCondition;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.ko.mediate.HC.tutee.domain.QTutee.tutee;

@Repository
public class JpaTuteeCustomRepositoryImpl extends QuerydslRepositorySupport
    implements JpaTuteeCustomRepository {
  private final JPAQueryFactory queryFactory;

  public JpaTuteeCustomRepositoryImpl(JPAQueryFactory queryFactory) {
    super(Tutee.class);
    this.queryFactory = queryFactory;
  }

  @Override
  public List<Tutee> findTutorOrderByDistance(
      PageRequest pageRequest, DistanceCondition condition) {
    return queryFactory
        .selectFrom(tutee)
        .where(
            Expressions.numberTemplate(
                    Double.class,
                    "function('ST_Distance_Sphere', {0}, POINT({1}, {2}))",
                    tutee.location,
                    condition.getLongitude(),
                    condition.getLatitude())
                .loe(condition.getRadius() * 1000))
        .orderBy(
            Expressions.numberTemplate(
                    Double.class,
                    "function('ST_Distance_Sphere', {0}, POINT({1}, {2}))",
                    tutee.location,
                    condition.getLongitude(),
                    condition.getLatitude())
                .asc())
        .fetch();
  }
}
