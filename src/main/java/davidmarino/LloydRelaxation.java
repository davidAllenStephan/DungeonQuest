package davidmarino;


/**
 * Class {@code LloydRelaxation} determines the centroid of a polygon and returns that point.
 * For more information about this algorithm read <A href="https://en.wikipedia.org/wiki/Lloyd's_algorithm">https://en.wikipedia.org/wiki/Lloyd's_algorithm</A>
 * @author David Marino
 * @version 14 Jun 2025
 */

public class LloydRelaxation {

    public static Point findCentroid(Polygon polygon, Point site) {
        double sumX = 0;
        double sumY = 0;
        for (Point p : polygon.vertices) {
            sumX += p.x;
            sumY += p.y;
        }
        int size = polygon.vertices.size();
        return new Point(sumX / size, sumY / size, site.z);
    }
}
