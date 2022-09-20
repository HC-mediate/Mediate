package com.ko.mediate.HC.community.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_article")
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    private String writeBy;

    @ColumnDefault("0")
    @Column(name = "view_count")
    private Long view;

    @ColumnDefault("0")
    @Column(name = "like_count")
    private Long like;

    @OneToMany(
            mappedBy = "article",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ArticleImage> articleImageList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Article(String title, String content, String writeBy, Category category) {
        this.title = title;
        this.content = content;
        this.writeBy = writeBy;
        this.category = category;
    }

    public void addArticleImage(ArticleImage articleImage) {
        if (Objects.nonNull(articleImage)) {
            articleImage.changeArticleImage(this);
            this.articleImageList.add(articleImage);
        }
    }
}
