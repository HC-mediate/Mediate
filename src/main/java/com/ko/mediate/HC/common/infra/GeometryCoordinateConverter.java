package com.ko.mediate.HC.common.infra;

import com.ko.mediate.HC.common.domain.GeometryConverter;
import com.ko.mediate.HC.common.domain.Location;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class GeometryCoordinateConverter implements GeometryConverter {
  private final GeometryFactory geometryFactory;

  public GeometryCoordinateConverter() {
    this.geometryFactory = new GeometryFactory();
  }

  @Override
  public Point convertCoordinateToPoint(double latitude, double longitude) {
    return this.geometryFactory.createPoint(new Coordinate(longitude, latitude));
  }

  @Override
  public Point convertCoordinateToPoint(Location location) {
    return this.geometryFactory.createPoint(
        new Coordinate(location.getLongitude(), location.getLatitude()));
  }
}
