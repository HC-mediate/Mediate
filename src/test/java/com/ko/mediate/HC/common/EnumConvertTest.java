package com.ko.mediate.HC.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ko.mediate.HC.tutor.application.dto.request.TutorSignupDto;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumConvertTest {

    @Test
    void enum을_json으로_직렬화시_커리큘럼_이름을_반환한다() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(Curriculum.ELEMENT_MATHEMATICS);
        assertThat(json).contains("초등수학");
    }

    @Test
    void json에서_enum으로_역직렬화시_enum객체를_반환한다() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String json = "{\"school\":\"아무학교\", \"major\": \"아무학과\", \"grade\":\"아무학년\", \"address\":\"아무주소\", \"curriculums\":[\"초등수학\", \"중등수학\"]," +
                "\"location\":{\"latitude\":127.20, \"longitude\": 130.32}}";
        TutorSignupDto dto = om.readValue(json, TutorSignupDto.class);
        assertThat(dto.getCurriculums().contains(Curriculum.ELEMENT_MATHEMATICS)).isTrue();
    }
}
