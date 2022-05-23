package com.ko.mediate.HC.tutoring.domain;

import com.ko.mediate.HC.tutoring.application.RoleType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TutoringPublishedEvent extends ApplicationEvent {
  private final Tutoring tutoring;
  private final RoleType currentRole;
  private String sendToAccountEmail;
  private String fromToAccountEmail;
  private String title;
  private String body;

  public TutoringPublishedEvent(Object source) {
    super(source);
    this.tutoring = (Tutoring) source;
    this.currentRole = tutoring.getCurrentUserRole();
    setSendToAccountId();
    setPushMessage();
  }

  private void setSendToAccountId() {
    if (currentRole == RoleType.ROLE_TUTOR) {
      this.sendToAccountEmail = tutoring.getTuteeEmail();
      this.fromToAccountEmail = tutoring.getTutorEmail();
    } else if (currentRole == RoleType.ROLE_TUTEE) {
      this.sendToAccountEmail = tutoring.getTutorEmail();
      this.fromToAccountEmail = tutoring.getTuteeEmail();
    } else {
      this.sendToAccountEmail = null;
    }
  }

  private void setPushMessage(){
    TutoringStat stat = tutoring.getStat();
    if(TutoringStat.WAITING_ACCEPT == stat){
      title = fromToAccountEmail + "님이 튜터링을 제안하였습니다.";
      body = "튜터링 이름: " + tutoring.getTutoringName();
    }
    else if(TutoringStat.LEARNING == stat){
      title = fromToAccountEmail + "님이 튜터링 제안을 수락하였습니다.";
      body = "이제부터 학습을 시작합니다!";
    }
    else if(TutoringStat.CANCEL == stat){
      title = fromToAccountEmail + "님이 튜터링 제안을 거절하거나 취소하였습니다.";
      body = "아쉽네요 ㅠㅠ";
    }
    else if(TutoringStat.COMPLETE_TUTORING == stat){
      title = fromToAccountEmail + "님과의 튜터링이 종료되었습니다.";
      body = "수고 많았습니다!";
    }
  }
}
