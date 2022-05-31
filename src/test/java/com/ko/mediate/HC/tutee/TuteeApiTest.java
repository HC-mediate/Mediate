package com.ko.mediate.HC.tutee;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static com.ko.mediate.HC.tutee.TuteeFactory.*;

import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.tutee.application.request.TuteeSignupDto;
import com.ko.mediate.HC.tutee.domain.Tutee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class TuteeApiTest extends BaseApiTest {
  @Autowired MockMvc mvc;

  @DisplayName("튜티 등록")
  @Test
  void tuteeJoinTest() throws Exception{
    //given
    TuteeSignupDto dto = createTuteeSignup();

    //when, then
    mvc.perform(post("/api/tutees")
      .header(AUTHORIZATION, normalAccessToken)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(dto)))
      .andExpect(status().isCreated())
      .andDo(print());

    Tutee tutee = tuteeRepository.findByAccountEmail("test_normal@naver.com").get();
    assertThat(tutee.getAddress()).isEqualTo(dto.getAddress());
    System.out.println(tutee.getLocation().getX());
  }
}
