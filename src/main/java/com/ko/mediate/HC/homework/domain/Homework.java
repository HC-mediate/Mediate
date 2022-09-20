package com.ko.mediate.HC.homework.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_homework")
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tutor_id", nullable = false)
    private String tutorId;

    @Column(name = "tutee_id", nullable = false)
    private String tuteeId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "homework_content", joinColumns = @JoinColumn(name = "homework_id"))
    private List<HomeworkContent> contents = new ArrayList<>();

    private String title;

    private Boolean isFinished;

    protected Homework() {
    }

    ;

    public Homework(String tutorId, String tuteeId, String title, boolean isFinished) {
        this.tutorId = tutorId;
        this.tuteeId = tuteeId;
        this.title = title;
        this.isFinished = isFinished;
    }

    public void giveHomework(String content, boolean isCompleted) {
        this.contents.add(new HomeworkContent(content, isCompleted));
    }

    public void changeHomework(String title, List<HomeworkContent> contents) {
        this.title = title;
        this.contents = contents;
    }
}
