package com.ko.mediate.HC.tutoring.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Tutoring {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tutoring_name")
  private String tutoringName;

  @Embedded
  @AttributeOverride(name = "accountId", column = @Column(name = "tutor_id"))
  private AccountId tutorId;

  @Embedded
  @AttributeOverride(name = "accountId", column = @Column(name = "tutee_id"))
  private AccountId tuteeId;

  @Column(name = "tutoring_stat")
  @Enumerated(EnumType.STRING)
  private TutoringStat stat;

  @Column(name = "started_at")
  private String startedAt;

  @Column(name = "finished_at")
  private String finishedAt;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "tutoring_id")
  private Set<Homework> homeworks = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "tutoring_id")
  private Set<Progress> progresses = new HashSet<>();

  protected Tutoring() {};

  public Tutoring(String tutorId, String tuteeId) {
    this.tutorId = new AccountId(tutorId);
    this.tuteeId = new AccountId(tuteeId);
    this.stat = TutoringStat.WAITING_ACCEPT;
  }

  @Builder
  public Tutoring(String tutoringName, String tutorId, String tuteeId, TutoringStat stat) {
    this.tutoringName = tutoringName;
    this.tutorId = new AccountId(tutorId);
    this.tuteeId = new AccountId(tuteeId);
    this.stat = stat;
  }

  public boolean isWaitingAcceptStat(){
    return this.stat == TutoringStat.WAITING_ACCEPT;
  }

  public boolean acceptTutoring() {
    if (this.stat != TutoringStat.WAITING_ACCEPT) {
      throw new IllegalArgumentException("수락 대기 중 상태가 아닙니다.");
    }
    this.stat = TutoringStat.LEARNING;
    this.startedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    return true;
  }

  public boolean cancelTutoring() {
    if (this.stat != TutoringStat.LEARNING) {
      throw new IllegalArgumentException("학습 중인 상태가 아닙니다.");
    }
    this.stat = TutoringStat.CANCEL;
    this.finishedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    return true;
  }

  public boolean completeTutoring() {
    if (this.stat != TutoringStat.LEARNING) {
      throw new IllegalArgumentException("튜터링이 진행 중 상태가 아닙니다.");
    }
    this.stat = TutoringStat.COMPLETE_TUTORING;
    this.finishedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    return true;
  }

  // 연관 관계 편의 메서드
  public void addHomework(Homework homework) {
    homework.changeTutoring(this);
    this.homeworks.add(homework);
  }
}
