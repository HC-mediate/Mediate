package com.ko.mediate.HC.tutee.infra;

import com.ko.mediate.HC.tutee.domain.Tutee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTuteeRepository extends JpaRepository<Tutee, Long>, JpaTuteeCustomRepository {

  @Query("SELECT t FROM Tutee t JOIN FETCH t.account a WHERE t.account.email = :accountEmail")
  Optional<Tutee> findByAccountEmail(@Param("accountEmail") String email);
}
