package com.ko.mediate.HC.tutoring;

import com.ko.mediate.HC.tutoring.domain.Curriculum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurriculumTest {
    @Test
    void CurriculumConvertTest() {
        Curriculum piano = Curriculum.PIANO;
        assertThat(piano == Curriculum.fromString("PIANO")).isTrue();
    }
}
