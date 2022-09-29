package com.ko.mediate.HC.aws.infra;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ko.mediate.HC.aws.domain.ArticleImageStorage;
import com.ko.mediate.HC.community.domain.AttachedImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ArticleImageS3Storage implements ArticleImageStorage {

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;

    @Value("${cloud.aws.cloud_front.domain_name}")
    private String CLOUD_FRONT;

    private final AmazonS3Client amazonS3Client;

    @Override
    public void deleteImages(List<AttachedImage> images) {
        images.stream().forEach(image -> amazonS3Client.deleteObject(BUCKET, image.getImageKey()));
    }

    @Override
    public List<AttachedImage> uploadImages(MultipartFile[] images) throws IOException {
        List<AttachedImage> list = new ArrayList<>();
        for (MultipartFile image : images) {
            String uploadKey = uploadImage(image);
            uploadKey = uploadKey.substring(uploadKey.indexOf(UPLOAD_DIRECTORY));
            String uploadUrl = CLOUD_FRONT + "/" + uploadKey;
            list.add(new AttachedImage(uploadKey, uploadUrl));
        }
        return list;
    }

    private String uploadImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        byte[] bytes = IOUtils.toByteArray(image.getInputStream());
        ByteArrayInputStream bytesArray = new ByteArrayInputStream(bytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        amazonS3Client.putObject(new PutObjectRequest(BUCKET, UPLOAD_DIRECTORY + fileName, bytesArray, metadata));
        return amazonS3Client.getUrl(BUCKET, UPLOAD_DIRECTORY + fileName).toString();
    }
}
