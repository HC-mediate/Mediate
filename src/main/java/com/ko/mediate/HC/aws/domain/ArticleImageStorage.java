package com.ko.mediate.HC.aws.domain;

import com.ko.mediate.HC.community.domain.AttachedImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleImageStorage {
    List<AttachedImage> uploadImages(MultipartFile[] images) throws IOException;
    void deleteImages(List<AttachedImage> keys);
}
