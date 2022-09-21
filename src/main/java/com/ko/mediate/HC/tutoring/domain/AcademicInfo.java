package com.ko.mediate.HC.tutoring.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class AcademicInfo {
    @Column(name = "school")
    private String school;

    @Column(name = "major", nullable = true)
    private String major;

    @Column(name = "grade")
    private String grade;

    public AcademicInfo(String school, String grade) {
        this.school = school;
        this.grade = grade;
    }

    protected AcademicInfo() {
    }

    ;

    public AcademicInfo(String school, String major, String grade) {
        this.school = school;
        this.major = major;
        this.grade = grade;
    }
}
