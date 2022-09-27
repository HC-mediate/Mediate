package com.ko.mediate.HC.community.application.dto.request;

import com.ko.mediate.HC.community.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestArticleDto {
    @NotEmpty
    private String title;
    private String content;
    private Category category;
    private MultipartFile[] images;
}
