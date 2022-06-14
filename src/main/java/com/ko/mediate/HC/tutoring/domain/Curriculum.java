package com.ko.mediate.HC.tutoring.domain;

import java.util.Arrays;

public enum Curriculum {
  ELEMENT_KOREAN("초등국어"),
  ELEMENT_ENGLISH("초등영어"),
  ELEMENT__MATHEMATICS("초등수학"),
  ELEMENT_SCIENCE("초등과학"),
  ELEMENT_SOCIETY("초등사회"),

  MIDDLE_KOREAN("중등국어"),
  MIDDLE_ENGLISH("중등영어"),
  MIDDLE_MATHEMATICS("중등수학"),
  MIDDLE_SOCIETY("중등사회"),
  MIDDLE_SCIENCE("중등과학"),

  HIGH_KOREAN("고등국어"),
  HIGH_ENGLISH("고등영어"),
  HIGH_MATHEMATICS("고등수학"),
  HIGH_SCIENCE("고등과학"),
  HIGH_SOCIETY("고등사회"),

  STUDENT_RECORD("학종"),
  COVER_LETTER("자기소개서"),
  CONSULTING("대입컨설팅"),
  INTERVIEW("대입면접"),
  PHOTOSHOP("포토샵"),
  ILLUSTRATOR("일러스트레이터"),
  CODDING("코딩"),

  ENGLISH_CONVERSATION("영어회화"),
  BUSINESS_ENGLISH("비즈니스영어"),
  CHINESE("중국어"),
  JAPANESE("일본어"),
  PRENCH("프랑스어"),
  SPANISH("스페인어"),
  GERMAN("독일어"),
  ARABIC("아랍어"),
  RUSSIAN("러시아어"),
  VIETNAMESE("베트남어"),

  PIANO("피아노"),
  BASE_GUITAR("베이스기타"),
  ELECT_GUITAR("일렉기타"),
  ACOUSTIC_GUITAR("통기타"),
  DRUM("드럼"),
  VIOLIN("바이올린"),
  CELLO("첼로"),
  VOCAL("보컬");
  private String curriculumName;

  Curriculum(String curriculumName) {
    this.curriculumName = curriculumName;
  }

  public static Curriculum fromString(String s) {
    return Arrays.stream(values())
        .filter(curriculum -> curriculum.name().equals(s))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown Value: " + s));
  }
}
