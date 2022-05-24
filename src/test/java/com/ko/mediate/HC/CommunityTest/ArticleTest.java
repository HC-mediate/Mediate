package com.ko.mediate.HC.CommunityTest;

import com.ko.mediate.HC.HcApplicationTests;
import com.ko.mediate.HC.common.infra.S3Uploader;
import com.ko.mediate.HC.community.application.dto.request.RequestArticleDto;
import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.domain.ArticleType;
import com.ko.mediate.HC.community.domain.Category;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import io.findify.s3mock.S3Mock;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(S3MockConfig.class)
public class ArticleTest extends HcApplicationTests {
  @Autowired private S3Uploader s3Uploader;
  @Autowired private S3Mock s3Mock;
  @Autowired private MockMvc mvc;
  private final String baseUrl = "/api/articles";

  @Test
  @DisplayName("파일 업로드 테스트 With S3 Mock")
  public void fileUploadTest() throws IOException {
    String expected = "mock1.png";
    MockMultipartFile mockMultipartFile =
        new MockMultipartFile("file", expected, "image/png", "test data".getBytes());
    String accessPath = s3Uploader.upload(mockMultipartFile);
    String ext = accessPath.substring(accessPath.lastIndexOf(".") + 1);
    assertThat(ext).isEqualTo("png");
  }

  @Test
  @DisplayName("글 생성 테스트")
  public void createArticleTest() throws Exception {
    RequestArticleDto dto = new RequestArticleDto("title", "content", ArticleType.QUESTION, Category.STUDY_QUESTION);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("imgFile", "cat.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
    MockMultipartFile dtoMutlipartFile = new MockMultipartFile("dto", null, MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsString(dto).getBytes(
        StandardCharsets.UTF_8));

    mvc.perform((multipart(baseUrl)
        .file(mockMultipartFile).part(new MockPart("imgFile", mockMultipartFile.getBytes()))
        .file(dtoMutlipartFile).part(new MockPart("dto", dtoMutlipartFile.getBytes()))
            .header("Authorization", BEARER + accessToken)
        .contentType(MediaType.MULTIPART_FORM_DATA)))
        .andDo(print())
        .andExpect(status().isOk());

    List<Article> articleList = articleRepository.findAll();
    assertThat(articleList.size()).isEqualTo(1);
  }

  @AfterEach
  public void shutdownMockS3() {
    s3Mock.stop();
  }
}
