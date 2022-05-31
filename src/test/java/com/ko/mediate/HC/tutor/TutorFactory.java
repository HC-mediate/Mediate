package com.ko.mediate.HC.tutor;

import com.ko.mediate.HC.tutor.application.request.TutorSignupDto;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import java.util.List;

public class TutorFactory {
  public static TutorSignupDto createTutorSignup() {
    return new TutorSignupDto(
        "test-school",
        "test-major",
        "3-grade",
        "seoul",
        List.of(Curriculum.ELEMENT, Curriculum.MIDDLE, Curriculum.HIGH),
        120.73,
        32.22);
  }
}
