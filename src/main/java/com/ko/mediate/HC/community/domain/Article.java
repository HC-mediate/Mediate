package com.ko.mediate.HC.community.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tb_article")
@DynamicInsert
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
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

    protected Article() {
    }

    @Builder
    public Article(String title, String content, String writeBy, Category category) {
        this.title = title;
        this.content = content;
        this.writeBy = writeBy;
        this.category = category;
    }

    public void addArticleImage(ArticleImage articleImage) {
        if (articleImage != null) {
            articleImage.changeArticle(this);
            this.articleImageList.add(articleImage);
        }
    }

    public void visitArticle() {
        this.view++;
    }

    public void pushLike() {
        this.like++;
    }
}
