package com.ko.mediate.HC.factory.dto;

import com.ko.mediate.HC.community.application.dto.request.CreateArticleDto;
import com.ko.mediate.HC.community.domain.Category;
import org.springframework.web.multipart.MultipartFile;

public class ArticleDtoFactory {
    public static CreateArticleDto createRequestArticleDto(String title, String content, Category category, MultipartFile[] images){
        return new CreateArticleDto(title, content, category, images);
    }
}
