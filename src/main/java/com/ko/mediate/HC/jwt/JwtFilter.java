package com.ko.mediate.HC.jwt;

import static com.ko.mediate.HC.common.AuthenticationExceptionUtils.EXCEPTION_ATTRIBUTE_KEY;

import com.ko.mediate.HC.auth.application.AuthService;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.exception.MediateExpiredTokenException;
import com.ko.mediate.HC.common.exception.MediateInvalidTokenException;
import com.ko.mediate.HC.common.exception.MediateNotFoundTokenException;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    private final TokenProvider tokenProvider;
    private final TokenStorage tokenStorage;
    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {
            String accessToken = resolveToken(httpServletRequest);
            if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
                UserInfo userInfo = tokenProvider.getUserInfoFromToken(accessToken);
                if (hasNotContainAccessTokenByAccountId(accessToken, userInfo)) {
                    throw new MediateNotFoundTokenException();
                }
                CustomUserDetails userDetails = authService.loadUserByUsername(
                        userInfo.getAccountEmail());
                setUserAuthenticationToken(userDetails);
            }
        } catch (MediateExpiredTokenException e) {
            request.setAttribute(EXCEPTION_ATTRIBUTE_KEY, e.getErrorCode());
        } catch (MediateNotFoundTokenException e) {
            request.setAttribute(EXCEPTION_ATTRIBUTE_KEY, e.getErrorCode());
        } catch (MediateInvalidTokenException e) {
            request.setAttribute(EXCEPTION_ATTRIBUTE_KEY, e.getErrorCode());
        }

        chain.doFilter(request, response);
    }

    private boolean hasNotContainAccessTokenByAccountId(String accessToken,
            UserInfo userInfo) {
        return !Optional.ofNullable(tokenStorage.getAccessTokenById(userInfo.getAccountId()))
                .filter(extractToken -> extractToken.equals(accessToken))
                .isPresent();
    }

    private void setUserAuthenticationToken(CustomUserDetails userDetails) {
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new CustomAuthenticationToken(userDetails, userDetails.getAuthorities()));
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        throw new MediateNotFoundTokenException();
    }
}
