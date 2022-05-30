package com.ko.mediate.HC.tutee.domain;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Table(name = "tb_tutee")
public class Tutee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  private Account account;

  @Embedded private AcademicInfo academicInfo;

  @Column(name = "address")
  private String address;

  @Column(name = "location")
  private Point location;

  protected Tutee() {}
  ;

  @Builder
  public Tutee(Account account, String address, String school, String major, String grade, Point location){
    this.account = account;
    this.academicInfo = new AcademicInfo(school, major, grade);
    this.address = address;
    this.location = location;
  }
}
