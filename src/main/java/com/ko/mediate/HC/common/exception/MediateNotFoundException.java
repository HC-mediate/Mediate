package com.ko.mediate.HC.common.exception;

import org.springframework.http.HttpStatus;

public class MediateNotFoundException extends MediateException {

  @Override
  public HttpStatus status() {
    return HttpStatus.NOT_FOUND;
  }

  public MediateNotFoundException() {
    super("해당 ID의 엔티티를 찾을 수 없습니다.");
  }

  public MediateNotFoundException(String message) {
    super(message);
  }
}
