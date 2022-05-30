package com.ko.mediate.HC.auth.resolver;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.common.exception.MediateNotFoundToken;
import com.ko.mediate.HC.jwt.TokenProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginMethodArgumentResolver implements HandlerMethodArgumentResolver {

  private final TokenProvider tokenProvider;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(LoginUser.class) != null
        && parameter.getParameterType().equals(UserInfo.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory)
      throws RuntimeException {
    String authorization = webRequest.getHeader("Authorization");
    if (authorization == null) {
      throw new MediateNotFoundToken("인증 토큰 값이 없습니다.");
    }

    return tokenProvider.getUserInfoFromToken(authorization.substring(7));
  }
}
