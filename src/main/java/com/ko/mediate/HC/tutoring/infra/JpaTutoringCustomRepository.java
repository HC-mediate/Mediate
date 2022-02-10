package com.ko.mediate.HC.tutoring.infra;

import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import java.util.Optional;

public interface JpaTutoringCustomRepository {
  Optional<Tutoring> findTutoringByAccountIdAndRole(
      Long tutoringId, String accountId, RoleType roleType);
}
