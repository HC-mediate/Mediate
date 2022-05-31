package com.ko.mediate.HC.tutee;

import com.ko.mediate.HC.tutee.application.request.TuteeSignupDto;

public class TuteeFactory {
  public static TuteeSignupDto createTuteeSignup() {
    return new TuteeSignupDto("test-school", "test-major", "2-grade", "bucheon", 120.73, 32.33);
  }
}
