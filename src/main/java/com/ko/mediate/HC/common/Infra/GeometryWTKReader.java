package com.ko.mediate.HC.common.Infra;

import com.ko.mediate.HC.common.domain.GeometryReader;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Component;

@Component
public class GeometryWTKReader implements GeometryReader {
  private final WKTReader wktReader;

  public GeometryWTKReader() {
    this.wktReader = new WKTReader();
  }

  @Override
  public Point convertCoordinateToPoint(double latitude, double longitude) throws ParseException {
    return (Point) this.wktReader.read(String.format("POINT(%s %s)", latitude, longitude));
  }
}
