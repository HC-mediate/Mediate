package com.ko.mediate.HC.facade.query;

import com.ko.mediate.HC.common.domain.Location;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
public class SearchCondition {
    private Location location;
    private int radius;

    public SearchCondition(Point location, int radius) {
        this.location = new Location(location.getX(), location.getY());
        this.radius = radius;
    }
}
