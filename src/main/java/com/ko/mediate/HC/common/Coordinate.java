package com.ko.mediate.HC.common;

import lombok.Getter;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Getter
public class Coordinate {
  private double latitude; // 위도: Y
  private double longitude; // 경도: X

  public Coordinate(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
  public static Point convertCoordinateToPoint(Coordinate coordinate) throws ParseException {
    String pointWKT = String.format("POINT(%s %s)", coordinate.getLatitude(), coordinate.getLongitude());
    return (Point) new WKTReader().read(pointWKT);
  }
}
