package com.ko.mediate.HC.community.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Table(name = "tb_article_img")
public class ArticleImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "access_path")
  private String accessPath;

  @Column(name = "orogin_file_name")
  private String originFileName;

  @Column(name = "create_at")
  @CreatedDate
  private LocalDateTime createAt;

  @ManyToOne private Article article;

  protected ArticleImage() {}

  public void changeArticle(Article article) {
    this.article = article;
  }

  public ArticleImage(String accessPath, String originFileName) {
    this.accessPath = accessPath;
    this.originFileName = originFileName;
  }
}
