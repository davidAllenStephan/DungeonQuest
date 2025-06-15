package davidmarino;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class {@code CoastMask}
 * @author David Marino
 * @version 14 Jun 2025
 */

public class CoastMask {

    /**
     * Finds the coast site points by checking that a polygon has at least one neighbor that is seaLevel and not all
     * neighbors are at seaLevel (isolated sea tile)
     * @param polygons
     * @return {@code ArrayList<Point>}
     */
    public void applyCoastMask(ArrayList<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            int seaNeighborCount = 0;
            if (polygon.site.z >= 0.5) {
                for (Polygon neighbor : polygon.neighbors) {
                    if (neighbor.site.z < 0.5) {
                        seaNeighborCount++;
                    }
                }
                if (0 < seaNeighborCount && seaNeighborCount < polygon.neighbors.size()) {
                    polygon.site.z = 0.99;
                }
            }
        }
    }
}
