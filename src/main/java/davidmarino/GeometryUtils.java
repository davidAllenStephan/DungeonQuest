package davidmarino;

import java.awt.*;
import java.util.ArrayList;

/**
 * The {@code GeometryUtils} class holds the calculations to generate a Voronoi model.
 * @version 13 Jun 2025
 * @author david marino
 */

public class GeometryUtils {

    /**
     * Clips polygon relative to Point site along bisection formed between site and other point.
     * @param polygon to mutate and starts encompassing the entire image
     * @param bisector of the site point and other
     * @param site point
     * @return {@code Polygon}
     */
    public static Polygon clipPolygon(Polygon polygon, Line bisector, Point site) {
        ArrayList<Point> outputList = new ArrayList<>();

        for (int i = 0; i < polygon.vertices.size(); i++) {
            Point current = polygon.vertices.get(i);
            Point prev = polygon.vertices.get((i + polygon.vertices.size() - 1) % polygon.vertices.size());

            boolean currInside = isInsideHalfPlane(current, bisector, site);
            boolean prevInside = isInsideHalfPlane(prev, bisector, site);

            if (currInside && prevInside) {
                outputList.add(current);
            } else if (prevInside && !currInside) {
                outputList.add(intersect(prev, current, bisector));
            } else if (!prevInside && currInside) {
                outputList.add(intersect(prev, current, bisector));
                outputList.add(current);
            }
        }
        return new Polygon(outputList, site);
    }

    /**
     * Determines if polygon point is within region created by bisection relative to the site point. This is done by
     * plugging in the point {@code p} into the bisector function. So y > mx + b -> 0 > mx + b - y. P is (0, 0),
     * m = 2 and b = 2. Then plugging in 0 > 2(0) + 2 - 0 -> 0 > 2, this is not true therefore it does not belong in
     * the region.
     * @param p is a point belonging to the polygon
     * @param bisector of the site point and other
     * @param site point
     * @return {@code boolean}
     */
    private static boolean isInsideHalfPlane(Point p, Line bisector, Point site) {
        double signP, signSite;

        if (bisector.isVertical) {
            double xLine = bisector.A.x;
            signP = p.x - xLine;
            signSite = site.x - xLine;
        } else {
            signP = p.y - (bisector.m * p.x + bisector.b);
            signSite = site.y - (bisector.m * site.x + bisector.b);
        }

        return signP * signSite >= 0;
    }

    /**
     * Finds the point where two points intersect relative to the bisector.
     * @param A point
     * @param B point
     * @param bisector of the site point and other
     * @return
     */
    public static Point intersect(Point A, Point B, Line bisector) {
        double dx = B.x - A.x;
        double dy = B.y - A.y;

        if (bisector.isVertical) {
            double xLine = bisector.A.x;
            if (Math.abs(dx) < 1e-10) return null; // segment is vertical, no intersection
            double t = (xLine - A.x) / dx;
            double y = A.y + t * dy;
            return new Point(xLine, y);
        }

        double denominator = dy - bisector.m * dx;
        if (Math.abs(denominator) < 1e-10) return null; // Lines are (almost) parallel

        double t = (bisector.m * A.x + bisector.b - A.y) / denominator;
        double x = A.x + t * dx;
        double y = A.y + t * dy;

        return new Point(x, y);
    }

}

