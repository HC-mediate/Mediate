package com.ko.mediate.HC.tutoring.domain;

import com.ko.mediate.HC.auth.domain.AccountId;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
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

  @Column(name = "tutor_email")
  private String tutorEmail;

  @Column(name = "tutee_email")
  private String tuteeEmail;

  @Column(name = "tutoring_stat")
  @Enumerated(EnumType.STRING)
  private TutoringStat stat;

  @Column(name = "started_at")
  private String startedAt;

  @Column(name = "finished_at")
  private String finishedAt;

  @OneToMany(
      mappedBy = "tutoring",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Progress> progresses = new ArrayList<>();

  @Column(name = "done_week")
  private Long doneWeek;

  @Column(name = "total_week")
  private Long totalWeek;

  @Transient private RoleType currentUserRole;

  protected Tutoring() {}
  ;

  @Builder
  public Tutoring(String tutoringName, String tutorEmail, String tuteeEmail) {
    this.tutoringName = tutoringName;
    this.tutorEmail = tutorEmail;
    this.tuteeEmail = tuteeEmail;
    this.stat = TutoringStat.WAITING_ACCEPT;
    this.totalWeek = 0L;
    this.doneWeek = 0L;
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

  public void addProgress(Progress progress) {
    this.progresses.add(progress);
    progress.changeTutoring(this);
    this.totalWeek++;
    if (progress.isComplete()) {
      this.doneWeek++;
    }
  }

  public void modifyProgress(long progressId, int week, String content, boolean isCompleted) {
    Progress progress =
        this.progresses.stream()
            .filter(p -> p.getId() == progressId)
            .findFirst()
            .orElseThrow(() -> new MediateNotFoundException("Progress ID를 찾을 수 없습니다."));
    progress.modifyProgress(week, content, isCompleted);
    if (progress.getIsCompleted()) {
      doneWeek++;
    }
  }

  public void removeProgress(long progressId) {
    Progress progress =
        this.progresses.stream()
            .filter(p -> p.getId() == progressId)
            .findFirst()
            .orElseThrow(() -> new MediateNotFoundException("Progress ID가 없습니다."));
    if (progress.isComplete()) {
      this.doneWeek--;
    }
    this.totalWeek--;
    this.progresses.remove(progress);
  }
}
