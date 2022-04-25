package com.ko.mediate.HC.community.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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

  @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ArticleImage> articleImageList = new ArrayList<>();

  @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Category> categories = new ArrayList<>();

  protected Article() {}

  @Builder
  public Article(
      String title, String content, String writeBy) {
    this.title = title;
    this.content = content;
    this.writeBy = writeBy;
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
