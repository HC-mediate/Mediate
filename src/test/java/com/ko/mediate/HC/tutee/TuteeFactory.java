package com.ko.mediate.HC.tutee;

import com.ko.mediate.HC.common.domain.Location;
import com.ko.mediate.HC.tutee.application.dto.request.TuteeSignupDto;

public class TuteeFactory {
    public static TuteeSignupDto createTuteeSignup() {
        return new TuteeSignupDto(
                "test-school", "test-major", "2-grade", "bucheon", new Location(34.22, 127.73));
    }
}
