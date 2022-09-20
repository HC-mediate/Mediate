package com.ko.mediate.HC.community.domain;

import lombok.AccessLevel;
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

    @Column(name = "image_key")
    private String imageKey;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    private Article article;

    public ArticleImage(String imageKey, String imageUrl, Article article) {
        this.imageKey = imageKey;
        this.imageUrl = imageUrl;
        this.article = article;
    }

    public void changeArticleImage(Article article) {
        if (Objects.nonNull(article)) {
            this.article = article;
        }
    }
}
