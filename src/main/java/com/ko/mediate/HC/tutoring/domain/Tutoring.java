package com.ko.mediate.HC.tutoring.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Tutoring {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

  @Column(name = "completed_at")
  private String completedAt;

  protected Tutoring() {};

  public Tutoring(String tutorId, String tuteeId) {
    this.tutorId = new AccountId(tutorId);
    this.tuteeId = new AccountId(tuteeId);
    this.stat = TutoringStat.WAITING_ACCEPT;
  }

  public boolean acceptTutoring(){
    if(this.stat != TutoringStat.WAITING_ACCEPT){
      throw new IllegalArgumentException("수락 대기 중 상태가 아닙니다.");
    }
    this.stat = TutoringStat.LEARNING;
    this.startedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    return true;
  }

  public boolean completeTutoring(){
    if(this.stat != TutoringStat.LEARNING){
      throw new IllegalArgumentException("튜터링이 진행 중 상태가 아닙니다.");
    }
    this.stat = TutoringStat.COMPLETE_TUTORING;
    this.completedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    return true;
  }
}
