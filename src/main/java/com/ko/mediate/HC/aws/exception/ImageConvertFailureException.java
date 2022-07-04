package com.ko.mediate.HC.aws.exception;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;

public class ImageConvertFailureException extends MediateException {

  public ImageConvertFailureException() {
    super(ErrorCode.IMAGE_CONVERT_FAILED);
  }

  @Override
  public HttpStatus status() {
    return null;
  }
}
