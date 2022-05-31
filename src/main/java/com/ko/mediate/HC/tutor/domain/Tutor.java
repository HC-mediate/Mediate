package com.ko.mediate.HC.tutor.domain;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

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

  @Transient private List<Curriculum> curriculums = new ArrayList<>();

  @Embedded private AcademicInfo academicInfo; // 튜터의 학생 정보

  @Column(name = "location", length = 1200)
  private Point location;

  protected Tutor() {}
  ;

  @Builder
  public Tutor(
      Account account,
      String address,
      List<Curriculum> curriculums,
      String school,
      String major,
      String grade,
      Point location) {
    this.account = account;
    this.address = address;
    this.curriculums = curriculums;
    this.location = location;
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
