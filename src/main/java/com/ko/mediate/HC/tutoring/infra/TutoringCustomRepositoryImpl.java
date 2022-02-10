package com.ko.mediate.HC.tutoring.infra;

import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.stereotype.Repository;

import static com.ko.mediate.HC.tutoring.domain.QTutoring.tutoring;

@Repository
public class TutoringCustomRepositoryImpl implements JpaTutoringCustomRepository {
  private final JPAQueryFactory queryFactory;

  public TutoringCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.queryFactory = jpaQueryFactory;
  }

  @Override
  public Optional<Tutoring> findTutoringByAccountIdAndRole(
      Long tutoringId, String accountId, RoleType roleType) {
    BooleanBuilder builder = nullSafeBuilder(() -> tutoring.id.eq(tutoringId));
    if (RoleType.ROLE_TUTOR == roleType) {
      builder.and(nullSafeBuilder(() -> tutoring.tutorId.accountId.eq(accountId)));
    } else {
      builder.and(nullSafeBuilder(() -> tutoring.tuteeId.accountId.eq(accountId)));
    }

    return Optional.ofNullable(queryFactory.selectFrom(tutoring).where(builder).fetchOne());
  }

  BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
    try {
      return new BooleanBuilder(f.get());
    } catch (Exception e) {
      return new BooleanBuilder();
    }
  }
}
