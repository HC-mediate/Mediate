package com.ko.mediate.HC.tutoring.domain;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    protected Tutoring() {
    }

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

    public void isTutoringMember(String email) {
        if (!this.tutorEmail.equals(email) && !this.tuteeEmail.equals(email)) {
            throw new MediateIllegalStateException(ErrorCode.NO_TUTORING_MEMBER);
        }
    }

    public void requestTutoring() {
        if (this.stat != TutoringStat.WAITING_ACCEPT) {
            throw new MediateIllegalStateException(ErrorCode.TUTORING_NO_WAIT_STATE);
        }
        publish();
    }

    public void acceptTutoring() {
        if (this.stat != TutoringStat.WAITING_ACCEPT) {
            throw new MediateIllegalStateException(ErrorCode.TUTORING_NO_WAIT_STATE);
        }
        this.stat = TutoringStat.LEARNING;
        this.startedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        publish();
    }

    public void cancelTutoring() {
        if (this.stat == TutoringStat.COMPLETE_TUTORING || this.stat == TutoringStat.CANCEL) {
            throw new MediateIllegalStateException(ErrorCode.TUTORING_CANCEL_STATE);
        }
        this.stat = TutoringStat.CANCEL;
        this.finishedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
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
                        .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
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
                        .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        if (progress.isComplete()) {
            this.doneWeek--;
        }
        this.totalWeek--;
        this.progresses.remove(progress);
    }
}
