package com.ko.mediate.HC.community.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tb_category")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Article article;

  private String name;

  protected Category() {}

  public void addArticle(Article article) {
    this.article = article;
  }
}
