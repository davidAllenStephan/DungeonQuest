package davidmarino.service.mapservice;

import davidmarino.model.mapmodels.Point;
import davidmarino.model.mapmodels.Polygon;

import java.util.ArrayList;

/**
 * Class {@code LloydRelaxation} determines the centroid of a polygon and returns that point.
 * For more information about this algorithm read <A href="https://en.wikipedia.org/wiki/Lloyd's_algorithm">https://en.wikipedia.org/wiki/Lloyd's_algorithm</A>
 * @author David Marino
 * @version 14 Jun 2025
 */
public class LloydRelaxation {

    /**
     * Finds the centroid of a polygon.
     * @param polygon to find centroid of
     * @return {@code Point}
     */
    private static Point findCentroid(Polygon polygon) {
        double sumX = 0;
        double sumY = 0;
        for (Point p : polygon.vertices) {
            sumX += p.x;
            sumY += p.y;
        }
        int size = polygon.vertices.size();
        return new Point(sumX / size, sumY / size, polygon.site.z);
    }

    /**
     * Applies Lloyd Relaxation to a set of points.
     * @param points to apply Lloyd Relaxation
     * @return {@code ArrayList<Point>}
     */
    public static ArrayList<Point> applyLloydRelaxation(ArrayList<Point> points, int numberOfLloydIterations, int width, int height) {
        PolygonService pcp = new PolygonService();
        for (int i = 0; i < numberOfLloydIterations; i++) {
            ArrayList<Point> adjustedSitePoints = new ArrayList<>();
            for (Point point : points) {
                Polygon p = pcp.computeVoronoiCell(point, points, width, height);
                adjustedSitePoints.add(findCentroid(p));
            }
            points = adjustedSitePoints;
        }
        return points;
    }
}
