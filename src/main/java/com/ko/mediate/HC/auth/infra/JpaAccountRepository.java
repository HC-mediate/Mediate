package com.ko.mediate.HC.auth.infra;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.oauth2.domain.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccountRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);

    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findAccountBySocialTypeAndSocialId(SocialType socialType, String socialId);

    boolean existsByNickname(String nickname);
}
