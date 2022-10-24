package com.ko.mediate.HC.tutor;

import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.tutor.application.dto.request.TutorSignupDto;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.ko.mediate.HC.tutor.TutorFactory.createTutorSignup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("튜터 api 테스트")
public class TutorApiTest extends BaseApiTest {
    @Autowired
    MockMvc mvc;

    @Test
    void 다수의_학습과목으로_튜터_등록시_201을_반환한다() throws Exception {
        //given
        TutorSignupDto dto = createTutorSignup();

        //when, then
        mvc.perform(
                        post("/api/tutors")
                                .header(AUTHORIZATION, normalAccessToken)
                                .content(objectMapper.writeValueAsString(dto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        Tutor tutor = tutorRepository.findTutorByAccountEmail("test_normal@naver.com").get();
        assertThat(tutor.getAddress()).isEqualTo(dto.getAddress());
        assertThat(tutor.getCurriculums()).contains(Curriculum.HIGH_MATHEMATICS, Curriculum.HIGH_KOREAN, Curriculum.HIGH_ENGLISH);
    }
}
