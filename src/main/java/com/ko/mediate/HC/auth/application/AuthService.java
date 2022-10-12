package com.ko.mediate.HC.auth.application;

import com.amazonaws.util.StringUtils;
import com.ko.mediate.HC.auth.application.dto.request.OAuth2SignInDto;
import com.ko.mediate.HC.auth.application.dto.request.OAuth2SignUpDto;
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
import com.ko.mediate.HC.oauth2.application.OAuth2Service;
import com.ko.mediate.HC.oauth2.domain.Profile;
import com.ko.mediate.HC.oauth2.domain.SocialType;
import com.ko.mediate.HC.tutoring.application.RoleType;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final JpaAccountRepository accountRepository;

    private final TokenProvider tokenProvider;

    private final TokenStorage tokenStorage;

    private final PasswordEncoder passwordEncoder;

    private final AccountService accountService;

    private final OAuth2Service oAuth2Service;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws AuthenticationException {
        Account account = getAccount(email);
        return createCustomUserDetails(account);
    }

    public TokenDto signIn(SignInDto dto) {
        Account account = getAccount(dto.getEmail());
        authenticate(account.getPassword(), dto.getPassword());
        String refreshToken = tokenProvider.getRefreshToken(account);
        String accessToken = tokenProvider.getAccessToken(account);
        return new TokenDto(refreshToken, accessToken);
    }

    public TokenDto oauth2SignIn(OAuth2SignInDto dto) {
        Profile oauth2Profile = oAuth2Service.getProfile(dto.getSocialType(), dto.getAccessToken());
        Account account = getAccount(dto.getSocialType(), oauth2Profile.getSocialId());
        String refreshToken = tokenProvider.getRefreshToken(account);
        String accessToken = tokenProvider.getAccessToken(account);
        return new TokenDto(refreshToken, accessToken);
    }

    public void oauth2SignUp(OAuth2SignUpDto dto) {
        Profile oauth2Profile = oAuth2Service.getProfile(dto.getSocialType(), dto.getAccessToken());
        accountService.saveAccount(dto, oauth2Profile);
    }

    public TokenDto reissueAccessToken(String refreshToken) {
        assert tokenProvider.isValidToken(refreshToken);
        UserInfo userInfo = tokenProvider.getUserInfoFromToken(refreshToken);
        if (StringUtils.isNullOrEmpty(tokenStorage.getRefreshTokenById(userInfo.getAccountId()))) {
            throw new MediateInvalidTokenException();
        }
        String accessToken = createAccessTokenIfExpired(userInfo);
        return new TokenDto(null, accessToken);
    }

    public void logout(UserInfo userInfo) {
        tokenStorage.deleteRefreshAndAccessTokenById(userInfo.getAccountId());
    }

    private Account getAccount(String email) {
        return accountRepository
                .findAccountByEmail(email)
                .orElseThrow(() -> new MediateNotFoundException(ErrorCode.NO_ACCOUNT_EXISTS));
    }

    private Account getAccount(SocialType socialType, String socialId) {
        return accountRepository
                .findAccountBySocialTypeAndSocialId(socialType, socialId)
                .orElseThrow(() -> new MediateNotFoundException(ErrorCode.NO_ACCOUNT_EXISTS));
    }

    private CustomUserDetails createCustomUserDetails(Account account) {
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

    private void authenticate(String encodePassword, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new AccountIncorrectPasswordException(ErrorCode.INCORRECT_PASSWORD);
        }
    }

    private String createAccessTokenIfExpired(UserInfo userInfo) {
        String accessToken = tokenStorage.getAccessTokenById(userInfo.getAccountId());
        if (tokenProvider.isValidToken(accessToken)) {
            return accessToken;
        }
        return tokenProvider.getAccessToken(userInfo);
    }
}
