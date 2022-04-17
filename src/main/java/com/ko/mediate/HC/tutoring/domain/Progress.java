package com.ko.mediate.HC.tutoring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_progress")
public class Progress {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Tutoring tutoring;

  @Column(name = "week")
  private Integer week;

  @Column(name = "content")
  private String content;

  @Column(name = "is_completed")
  private Boolean isCompleted;

  protected Progress() {};

  public Progress(int week, String content, boolean isComplete) {
    this.week = week;
    this.content = content;
    this.isCompleted = isComplete;
  }

  public void complete() {
    this.isCompleted = true;
  }

  public void notComplete() {
    this.isCompleted = false;
  }

  public Boolean isComplete() {
    return this.isCompleted;
  }

  // 연관관계 편의 메서드
  public void changeTutoring(Tutoring tutoring) {
    this.tutoring = tutoring;
  }
}
