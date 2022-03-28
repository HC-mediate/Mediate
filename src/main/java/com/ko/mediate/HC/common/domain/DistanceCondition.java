package com.ko.mediate.HC.common.domain;

import lombok.Getter;

@Getter
public class DistanceCondition {
  private double longitude;
  private double latitude;
  private int radius;

  public DistanceCondition(double longitude, double latitude, int radius) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.radius = radius;
  }
}
