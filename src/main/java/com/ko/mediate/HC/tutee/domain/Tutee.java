package com.ko.mediate.HC.tutee.domain;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "tb_tutee")
public class Tutee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Embedded
    private AcademicInfo academicInfo;

    @Column(name = "address")
    private String address;

    protected Tutee() {
    }

    ;

    @Builder
    public Tutee(Account account, String address, String school, String major, String grade) {
        this.account = account;
        this.academicInfo = new AcademicInfo(school, major, grade);
        this.address = address;
    }
}
