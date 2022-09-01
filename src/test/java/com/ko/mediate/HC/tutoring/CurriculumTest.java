package com.ko.mediate.HC.tutoring;

import static org.assertj.core.api.Assertions.*;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import org.junit.jupiter.api.Test;

public class CurriculumTest {
  @Test
  void CurriculumConvertTest(){
    Curriculum piano = Curriculum.PIANO;
    assertThat(piano == Curriculum.fromString("PIANO")).isTrue();
  }
}
