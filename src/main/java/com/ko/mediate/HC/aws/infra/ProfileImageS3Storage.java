package com.ko.mediate.HC.aws.infra;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ko.mediate.HC.aws.domain.ProfileImageStorage;
import com.ko.mediate.HC.common.exception.ImageConvertFailureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProfileImageS3Storage implements ProfileImageStorage {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${image.convert-path}")
    private String convertPath;

    private String uploadDirectory;
    private final AmazonS3Client amazonS3Client;

    @PostConstruct
    void init() {
        uploadDirectory = "profile/";
    }

    private String uploadS3(File uploadFile, String key) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, key, uploadFile));
        return amazonS3Client.getUrl(bucket, key).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile =
                new File(convertPath + File.separator + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos =
                         new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    @Override
    public String uploadFile(MultipartFile file, String key) throws IOException {
        File imageFile = convert(file).orElseThrow(ImageConvertFailureException::new);
        String url = uploadS3(imageFile, uploadDirectory + key);
        log.info("Profile Image Upload Url: {}", url);
        removeNewFile(imageFile);
        return uploadDirectory + key;
    }

    @Override
    public void deleteFile(String key) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        log.info("Delete Profile: {}", key);
    }
}
