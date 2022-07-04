package com.ko.mediate.HC.aws.exception;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;

public class MediateUnsupportImageType extends MediateException {

  public MediateUnsupportImageType() {
    super(ErrorCode.UNSUPPORT_IMAGE_TYPE);
  }

  @Override
  public HttpStatus status() {
    return null;
  }
}
