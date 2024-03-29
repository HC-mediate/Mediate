package com.ko.mediate.HC.firebase.infra;

import com.ko.mediate.HC.firebase.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaFcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findFcmTokenByAccountEmail(String accountEmail);
}
