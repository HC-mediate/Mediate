package com.ko.mediate.HC.tutor;

import com.ko.mediate.HC.common.domain.Location;
import com.ko.mediate.HC.tutor.application.dto.request.TutorSignupDto;
import com.ko.mediate.HC.tutoring.domain.Curriculum;

import java.util.List;

public class TutorFactory {
    public static TutorSignupDto createTutorSignup() {
        return new TutorSignupDto(
                "test-school",
                "test-major",
                "3-grade",
                "seoul",
                List.of(Curriculum.HIGH_MATHEMATICS, Curriculum.HIGH_KOREAN, Curriculum.HIGH_ENGLISH),
                new Location(34.22, 127.73));
    }
}
