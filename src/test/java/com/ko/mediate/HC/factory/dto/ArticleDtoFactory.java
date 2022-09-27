package com.ko.mediate.HC.factory.dto;

import com.ko.mediate.HC.community.application.dto.request.RequestArticleDto;
import com.ko.mediate.HC.community.domain.Category;
import org.springframework.web.multipart.MultipartFile;

public class ArticleDtoFactory {
    public static RequestArticleDto createRequestArticleDto(String title, String content, Category category, MultipartFile[] images){
        return new RequestArticleDto(title, content, category, images);
    }
}
