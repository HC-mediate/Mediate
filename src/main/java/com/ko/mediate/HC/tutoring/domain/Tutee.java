package com.ko.mediate.HC.tutoring.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tb_tutee")
public class Tutee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private TuteeInfo tuteeInfo;

  @Embedded
  private AccountId accountId;

  @Column(name = "address")
  private String address;

  protected Tutee() {};

  public Tutee(String address, TuteeInfo tuteeInfo) {
    this.tuteeInfo = tuteeInfo;
    this.address = address;
  }
}
