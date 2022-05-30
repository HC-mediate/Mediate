package com.ko.mediate.HC.aws.exception;

import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;

public class MediateUnsupportImageType extends MediateException {

  public MediateUnsupportImageType() {
    super("지원하지 않는 이미지 타입입니다.");
  }

  public MediateUnsupportImageType(String message) {
    super(message);
  }

  @Override
  public HttpStatus status() {
    return null;
  }
}
