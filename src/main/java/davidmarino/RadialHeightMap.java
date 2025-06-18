package davidmarino;

import davidmarino.model.Point;
import davidmarino.model.Polygon;

import java.util.ArrayList;

/**
 * Used for producing height map.
 * @author David Marino
 * @version 13 Jun 2025
 */
public class RadialHeightMap {

    /**
     * Adjusts z index of site point of polygon depending on the distance from the center.
     * @param point site
     */
    public void adjustToRadial(Point point) {
        double midX = Parameters.width / 2.0;
        double midY = Parameters.height / 2.0;
        double distFromMidToCorner = Math.sqrt(Math.pow(midX, 2) + Math.pow(midY, 2));

        double distToMidPoint = Math.sqrt(Math.pow(midX - point.x, 2) + Math.pow(midY - point.y, 2));
        double normDistToMidPoint = distToMidPoint/ distFromMidToCorner;
        point.z = 1.0 - normDistToMidPoint;
    }

    /**
     * Applies the z value of every polygons site which is used to determine elevation.
     * @param polygons to apply to
     */
    public static void applyRadialHeightMap(ArrayList<Polygon> polygons) {
        RadialHeightMap radialHeightMap = new RadialHeightMap();
        for (Polygon poly : polygons) {
            radialHeightMap.adjustToRadial(poly.site);
        }
    }

}
