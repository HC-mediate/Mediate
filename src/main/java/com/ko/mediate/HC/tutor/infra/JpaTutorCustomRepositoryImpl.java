package com.ko.mediate.HC.tutor.infra;

import com.ko.mediate.HC.common.domain.DistanceCondition;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.ko.mediate.HC.tutor.domain.QTutor.tutor;

@Repository
public class JpaTutorCustomRepositoryImpl extends QuerydslRepositorySupport
    implements JpaTutorCustomRepository {
  private final JPAQueryFactory queryFactory;

  public JpaTutorCustomRepositoryImpl(JPAQueryFactory queryFactory) {
    super(Tutor.class);
    this.queryFactory = queryFactory;
  }

  @Override
  public List<Tutor> findTutorOrderByDistance(
      PageRequest pageRequest, DistanceCondition condition) {
    return queryFactory
        .selectFrom(tutor)
        .where(
            Expressions.numberTemplate(
                    Double.class,
                    "function('ST_Distance_Sphere', {0}, POINT({1}, {2}))",
                    tutor.location,
                    condition.getLongitude(),
                    condition.getLatitude())
                .loe(condition.getRadius() * 1000))
        .orderBy(
            Expressions.numberTemplate(
                    Double.class,
                    "function('ST_Distance_Sphere', {0}, POINT({1}, {2}))",
                    tutor.location,
                    condition.getLongitude(),
                    condition.getLatitude())
                .asc())
        .fetch();
  }
}
