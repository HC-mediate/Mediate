package com.ko.mediate.HC.tutoring.infra;

import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.ko.mediate.HC.tutoring.domain.QTutoring.tutoring;

@Repository
public class JpaTutoringCustomRepositoryImpl extends QuerydslRepositorySupport implements JpaTutoringCustomRepository {
  private final JPAQueryFactory queryFactory;

  public JpaTutoringCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Tutoring.class);
    this.queryFactory = jpaQueryFactory;
  }
  public Optional<Tutoring> findTutoringByAccountIdAndRole(Long tutoringId, String accountEmail, RoleType roleType){
    return Optional.ofNullable(queryFactory.selectFrom(tutoring)
      .where(tutoring.id.eq(tutoringId), eqEmailWithRoleType(accountEmail, roleType))
      .fetchOne());
  }

  private BooleanExpression eqEmailWithRoleType(String accountEmail, RoleType roleType){
    if(roleType == RoleType.ROLE_TUTOR){
      return tutoring.tutorEmail.eq(accountEmail);
    }
    else{
      return tutoring.tuteeEmail.eq(accountEmail);
    }
  }
}
