package com.ko.mediate.HC.tutor.domain;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "tb_tutor")
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "address")
    private String address;

    @Column(name = "curriculum")
    @Getter(AccessLevel.PROTECTED)
    private String curriculum; // 교과 과정

    @Transient
    private List<Curriculum> curriculums = new ArrayList<>();

    @Embedded
    private AcademicInfo academicInfo; // 튜터의 학생 정보

    protected Tutor() {
    }

    ;

    @Builder
    public Tutor(
            Account account,
            String address,
            List<Curriculum> curriculums,
            String school,
            String major,
            String grade) {
        this.account = account;
        this.address = address;
        this.curriculums = curriculums;
        this.academicInfo = new AcademicInfo(school, major, grade);
    }

    @PrePersist
    void enumListToString() {
        this.curriculum =
                String.join(
                        ",", curriculums.stream().map(Curriculum::toString).collect(Collectors.toList()));
    }

    @PostLoad
    void stringToEnumList() {
        this.curriculums =
                Arrays.stream(curriculum.split(","))
                        .map(Curriculum::fromString)
                        .collect(Collectors.toList());
    }
}
