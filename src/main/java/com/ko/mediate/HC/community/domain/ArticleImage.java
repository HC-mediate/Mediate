package com.ko.mediate.HC.community.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_article_img")
public class ArticleImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    AttachedImage attachedImage;

    @ManyToOne
    private Article article;

    @Builder
    public ArticleImage(AttachedImage attachedImage, Article article) {
        this.attachedImage = attachedImage;
        this.article = article;
    }

    public void changeArticleImage(Article article) {
        if (Objects.nonNull(article)) {
            this.article = article;
        }
    }
}
