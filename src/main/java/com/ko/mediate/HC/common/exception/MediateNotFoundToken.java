package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class MediateNotFoundToken extends MediateException {
  @Override
  public HttpStatus status() {
    return HttpStatus.UNAUTHORIZED;
  }

  public MediateNotFoundToken(ErrorCode errorCode) {
    super(errorCode);
  }
}
