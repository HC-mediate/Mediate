package com.ko.mediate.HC.tutor.Infra;

import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutor.domain.TutorRepository;
import java.util.List;
import java.util.Optional;
import org.locationtech.jts.geom.Point;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Profile(value = {"local-maria", "prod1", "prod2"})
@Repository
public interface JpaTutorRepositoryWithMaria extends JpaRepository<Tutor, Long>, TutorRepository {
  @Query("SELECT t FROM Tutor t WHERE t.accountId.accountId = :accountId")
  Optional<Tutor> findByAccountId(@Param("accountId") String accountId);

  @Query(
      "SELECT t, a FROM Tutor t, Account a WHERE t.accountId.accountId = :accountId and a.accountId.accountId = :accountId")
  List<Object[]> findTutorAccountInfoById(@Param("accountId") String accountId);

  @Query(value = "SELECT t.id, t.account_id, t.address, t.name, t.grade, t.major, t.school, t.curriculum, t.location "
      + "FROM tb_tutor as t WHERE ST_Distance_Sphere(t.location, :curLocation) <= :radius "
      + "ORDER BY ST_Distance_Sphere(:curLocation, t.location) limit :startAt, :size", nativeQuery = true)
  List<Tutor> findTutorOrderByDistance(@Param("curLocation") Point location, @Param("radius") Integer radius,
      @Param("startAt") Integer startAt, @Param("size") Integer size);
}
