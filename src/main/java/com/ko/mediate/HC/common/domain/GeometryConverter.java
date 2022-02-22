package com.ko.mediate.HC.common.domain;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;

public interface GeometryConverter {
  Point convertCoordinateToPoint(double latitude, double longitude) throws ParseException;
}
