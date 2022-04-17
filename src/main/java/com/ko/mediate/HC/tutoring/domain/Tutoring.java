package com.ko.mediate.HC.tutoring.domain;

import com.ko.mediate.HC.auth.domain.AccountId;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.tutoring.application.RoleType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Getter
@Table(name = "tb_tutoring")
public class Tutoring extends AbstractAggregateRoot<Tutoring> {
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
  private List<Progress> progresses = new ArrayList<>();

  @Transient private RoleType currentUserRole;

  protected Tutoring() {};

  @Builder
  public Tutoring(String tutoringName, String tutorId, String tuteeId) {
    this.tutoringName = tutoringName;
    this.tutorId = new AccountId(tutorId);
    this.tuteeId = new AccountId(tuteeId);
    this.stat = TutoringStat.WAITING_ACCEPT;
  }

  public void requestTutoring(RoleType roleType) {
    if (this.stat != TutoringStat.WAITING_ACCEPT) {
      throw new MediateIllegalStateException("수락 대기 중 상태가 아닙니다.");
    }
    this.currentUserRole = roleType;
    publish();
  }

  public void acceptTutoring(RoleType roleType) {
    if (this.stat != TutoringStat.WAITING_ACCEPT) {
      throw new MediateIllegalStateException("수락 대기 중 상태가 아닙니다.");
    }
    this.stat = TutoringStat.LEARNING;
    this.startedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    this.currentUserRole = roleType;
    publish();
  }

  public void cancelTutoring(RoleType roleType) {
    if (this.stat == TutoringStat.COMPLETE_TUTORING || this.stat == TutoringStat.CANCEL) {
      throw new MediateIllegalStateException("학습 중이거나 대기 중이여야 합니다.");
    }
    this.stat = TutoringStat.CANCEL;
    this.finishedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    this.currentUserRole = roleType;
    publish();
  }

  // 엔티티 수정 메서드
  public void changeTutoringName(String tutoringName) {
    this.tutoringName = tutoringName;
  }

  // 이벤트 발행 메서드
  private Tutoring publish() {
    this.registerEvent(new TutoringPublishedEvent(this));
    return this;
  }

  public void addProgress(Progress progress){
    this.progresses.add(progress);
    progress.changeTutoring(this);
  }

  public void modifyProgress(long tutoringId, Progress progress){
    this.progresses.removeIf(p -> p.getId() == tutoringId);
    this.progresses.add(progress);
    progress.changeTutoring(this);
  }

  public void removeProgress(long tutoringId){
    this.progresses.removeIf(p -> p.getId() == tutoringId);
  }
}
