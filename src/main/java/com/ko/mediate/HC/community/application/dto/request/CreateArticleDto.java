package com.ko.mediate.HC.community.application.dto.request;

import com.ko.mediate.HC.community.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleDto {
    private String title;
    private String content;
    private Category category;
    private List<MultipartFile> images = new ArrayList<>();
}
