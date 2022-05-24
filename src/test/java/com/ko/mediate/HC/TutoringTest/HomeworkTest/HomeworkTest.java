package com.ko.mediate.HC.TutoringTest.HomeworkTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ko.mediate.HC.HcApplicationTests;
import com.ko.mediate.HC.homework.application.HomeworkContentDto;
import com.ko.mediate.HC.homework.application.request.CreateHomeworkDto;
import com.ko.mediate.HC.homework.application.request.UpdateHomeworkDto;
import com.ko.mediate.HC.homework.domain.Homework;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("숙제 CRUD 테스트")
public class HomeworkTest extends HcApplicationTests {

  @Autowired private MockMvc mvc;
  private Long homeworkId;

  @BeforeEach
  void setup() {
    Homework homework = new Homework("tutor1", "tutee1", "1주차 숙제", false);
    homework.giveHomework("수학 문제집 130p 풀기", false);
    homework.giveHomework("국어 문제집 10p 풀기", false);
    homeworkId = homeworkRepository.saveAndFlush(homework).getId();
  }

  @AfterEach
  void teardown() {
    homeworkRepository.deleteAll();
  }

  @DisplayName("숙제를 출제함")
  @ParameterizedTest
  @ValueSource(strings = {"tutee1"})
  public void giveHomeworkTest(String tuteeId) throws Exception {
    List<HomeworkContentDto> contents = new ArrayList<>();
    contents.add(new HomeworkContentDto("국어 18p 풀기", false));
    contents.add(new HomeworkContentDto("수학 2p 풀기", false));
    CreateHomeworkDto homework = new CreateHomeworkDto("tutor", tuteeId, "숙제 제목", contents);
    mvc.perform(
            post("/api/homework")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + accessToken)
                .content(objectMapper.writeValueAsString(homework)))
        .andExpect(status().isOk())
        .andDo(print());
    List<Homework> homeworks = homeworkRepository.findAllByTuteeId(tuteeId);
    assertThat(homeworks.size()).isEqualTo(2);
    assertThat(homeworks.get(0).getContents().size()).isEqualTo(contents.size());
  }

  @DisplayName("숙제를 확인함")
  @ParameterizedTest
  @CsvSource(
      value = {"/api/homework/tutee/:tutee1", "/api/homework/tutor/:tutor1"},
      delimiter = ':')
  public void getAllHomeworkTest(String url, String tuteeId) throws Exception {
    mvc.perform(get(url + tuteeId).header("Authorization", BEARER + accessToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("1주차 숙제"))
        .andExpect(jsonPath("$[0].homeworkId").value(homeworkId))
        .andDo(print());
  }

  @DisplayName("숙제를 수정함")
  @ParameterizedTest
  @CsvSource(
      value = {"/api/homework/:수정된 숙제 제목"},
      delimiter = ':')
  public void modifyHomeworkTest(String url, String expectTitle) throws Exception {
    UpdateHomeworkDto dto =
        new UpdateHomeworkDto(
            expectTitle, Arrays.asList(new HomeworkContentDto("수정된 숙제 내용", false)));
    mvc.perform(
            put(url + String.valueOf(homeworkId))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + accessToken)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andDo(print());

    Homework homework = homeworkRepository.findById(homeworkId).get();
    assertThat(homework.getTitle()).isEqualTo(expectTitle);
    assertThat(homework.getContents().get(0).getContent()).isEqualTo("수정된 숙제 내용");
  }
}
