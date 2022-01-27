package com.ko.mediate.HC.tutoring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Progress {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Tutoring tutoring;

  @Column(name = "content")
  private String content;

  @Column(name = "is_finished")
  private Boolean isFinished;

  protected Progress() {};

  public Progress(Tutoring tutoring, String content, Boolean isComplete) {
    this.tutoring = tutoring;
    this.content = content;
    this.isFinished = isComplete;
  }

  public void complete() {
    this.isFinished = true;
  }

  public void notComplete() {
    this.isFinished = false;
  }

  public boolean isComplete() {
    return this.isFinished.booleanValue();
  }

  // 연관관계 편의 메서드
  public void changeTutoring(Tutoring tutoring) {
    this.tutoring = tutoring;
  }
}
