package com.ko.mediate.HC.auth.application;

import com.amazonaws.util.StringUtils;
import com.ko.mediate.HC.auth.application.dto.request.SignInDto;
import com.ko.mediate.HC.auth.application.dto.response.TokenDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.AccountIncorrectPasswordException;
import com.ko.mediate.HC.common.exception.MediateInvalidTokenException;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.jwt.CustomUserDetails;
import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.jwt.TokenStorage;
import com.ko.mediate.HC.tutoring.application.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final JpaAccountRepository accountRepository;
    private final TokenProvider tokenProvider;
    private final TokenStorage tokenStorage;
    private final PasswordEncoder passwordEncoder;

    private Account findAccountByEmail(String email) {
        return accountRepository
                .findAccountByEmail(email)
                .orElseThrow(() -> new MediateNotFoundException(ErrorCode.NO_ACCOUNT_EXISTS));
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws AuthenticationException {
        return accountRepository
                .findAccountByEmail(email)
                .map(user -> createUser(user))
                .orElseThrow(() -> new BadCredentialsException("등록된 이메일이 없습니다."));
    }

    public TokenDto signIn(SignInDto dto) {
        Account account = findAccountByEmail(dto.getEmail());
        authenticate(account.getPassword(), dto.getPassword());
        String refreshToken =
                tokenProvider.createRefreshToken(account.getId(), dto.getEmail(), account.getNickname(), account.getRoles());
        String accessToken =
                tokenProvider.createAccessToken(account.getId(), dto.getEmail(), account.getNickname(), account.getRoles());
        tokenStorage.saveRefreshToken(refreshToken, account.getId());
        tokenStorage.saveAccessToken(accessToken, account.getId());
        return new TokenDto(refreshToken, accessToken);
    }

    private void authenticate(String encodePassword, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new AccountIncorrectPasswordException(ErrorCode.INCORRECT_PASSWORD);
        }
    }

    public CustomUserDetails createUser(Account account) {
        if (account.isDeactivated()) {
            throw new BadCredentialsException("활성화되지 않은 아이디입니다.");
        }
        Set<GrantedAuthority> grantedAuthorities =
                account.getRoles().stream()
                        .map(RoleType::toString)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
        return new CustomUserDetails(
                String.valueOf(account.getId()), account.getPassword(), grantedAuthorities);
    }

    public TokenDto reissueAccessTokenByRefreshToken(String refreshToken) {
        tokenProvider.validateToken(refreshToken);
        UserInfo userInfo = tokenProvider.getUserInfoFromToken(refreshToken);
        if (StringUtils.isNullOrEmpty(tokenStorage.getRefreshTokenById(userInfo.getAccountId()))) {
            throw new MediateInvalidTokenException();
        }
        String accessToken = createAccessTokenIfExpired(userInfo);
        return new TokenDto(null, accessToken);
    }

    private String createAccessTokenIfExpired(UserInfo userInfo) {
        String accessToken = tokenStorage.getAccessTokenById(userInfo.getAccountId());
        String reissueToken =
                tokenProvider.createAccessTokenIfExpired(
                        accessToken, userInfo.getAccountId(), userInfo.getAccountEmail(), userInfo.getAccountNickname(), userInfo.getRoles());
        if (accessToken.equals(reissueToken)) {
            return accessToken;
        } else {
            tokenStorage.saveAccessToken(reissueToken, userInfo.getAccountId());
            return reissueToken;
        }
    }

    public void logout(UserInfo userInfo) {
        tokenStorage.deleteRefreshAndAccessTokenById(userInfo.getAccountId());
    }
}
