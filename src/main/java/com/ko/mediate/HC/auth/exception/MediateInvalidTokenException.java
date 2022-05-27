package com.ko.mediate.HC.auth.exception;

import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;

public class MediateInvalidTokenException extends MediateException {

  public MediateInvalidTokenException() {
    super("서버가 무효화시킨 토큰입니다.");
  }

  public MediateInvalidTokenException(String message) {
    super(message);
  }

  @Override
  public HttpStatus status() {
    return null;
  }
}
