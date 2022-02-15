package com.ko.mediate.HC.firebase.infra;

import com.ko.mediate.HC.firebase.domain.FcmToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFcmTokenRepository extends JpaRepository<FcmToken, Long> {
  Optional<FcmToken> findFcmTokenByAccountId(String accountId);
}
