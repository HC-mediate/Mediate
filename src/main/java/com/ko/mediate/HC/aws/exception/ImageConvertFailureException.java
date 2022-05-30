package com.ko.mediate.HC.aws.exception;

import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;

public class ImageConvertFailureException extends MediateException {

  public ImageConvertFailureException() {
    super("파일 형식이 올바르지 않습니다.");
  }

  public ImageConvertFailureException(String message) {
    super(message);
  }

  @Override
  public HttpStatus status() {
    return null;
  }
}
