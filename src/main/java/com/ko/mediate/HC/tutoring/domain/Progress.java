package com.ko.mediate.HC.tutoring.domain;

import lombok.Getter;

import javax.persistence.*;

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
    private Long week;

    @Column(name = "content")
    private String content;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    protected Progress() {
    }

    ;

    public Progress(long week, String content, boolean isComplete) {
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

    public void modifyProgress(long week, String content, boolean isCompleted) {
        this.week = week;
        this.content = content;
        this.isCompleted = isCompleted;
    }

    public void changeTutoring(Tutoring tutoring) {
        this.tutoring = tutoring;
    }
}
