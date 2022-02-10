package com.ko.mediate.HC.common.exception;

import org.springframework.http.HttpStatus;

public class MediateNotFoundToken extends MediateException {
  @Override
  public HttpStatus status() {
    return HttpStatus.UNAUTHORIZED;
  }

  public MediateNotFoundToken() {}

  public MediateNotFoundToken(String message) {
    super(message);
  }
}
