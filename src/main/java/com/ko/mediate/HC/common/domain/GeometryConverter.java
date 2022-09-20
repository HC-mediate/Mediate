package com.ko.mediate.HC.common.domain;

import org.locationtech.jts.geom.Point;

public interface GeometryConverter {
    Point convertCoordinateToPoint(double latitude, double longitude);

    Point convertCoordinateToPoint(Location location);
}
