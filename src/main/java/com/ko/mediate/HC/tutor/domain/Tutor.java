package com.ko.mediate.HC.tutor.domain;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Table(name = "tb_tutor")
public class Tutor {
  @Id @GeneratedValue private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_email")
  private Account account;

  @Column(name = "address")
  private String address;

  @Column(name = "curriculum")
  @Enumerated(value = EnumType.STRING)
  private Curriculum curriculum; // 교과 과정

  @Embedded private AcademicInfo academicInfo; // 튜터의 학생 정보

  @Column(name = "location")
  private Point location;

  protected Tutor() {}
  ;

  @Builder
  public Tutor(Account account, String address, Curriculum curriculum, String school, String major, String grade, Point location){
    this.account = account;
    this.address = address;
    this.curriculum = curriculum;
    this.location = location;
    this.academicInfo = new AcademicInfo(school, major, grade);
  }
}
