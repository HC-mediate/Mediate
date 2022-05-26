package com.ko.mediate.HC.aws.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.s3.AmazonS3Client;
import com.ko.mediate.HC.CommunityTest.S3MockConfig;
import com.ko.mediate.HC.aws.infra.ProfileImageS3Storage;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest(classes = {S3MockConfig.class, ProfileImageS3Storage.class})
public class ProfileImageStorageTest {
  @Autowired ProfileImageStorage profileImageStorage;

  @Autowired AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  String bucket;

  @DisplayName("이미지 정상 업로드")
  @Test
  void imageUploadTest() throws IOException {
    // given
    String uploadKey = UUID.randomUUID().toString() + ".jpeg";
    MockMultipartFile file =
        new MockMultipartFile("file", "file.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[] {1});

    // when
    String uploadUrl = profileImageStorage.uploadFile(file, uploadKey);

    // then
    assertThat(uploadUrl).contains(uploadKey);
    assertThat(uploadUrl).contains(bucket + "/profile/" + uploadKey);
  }
}
