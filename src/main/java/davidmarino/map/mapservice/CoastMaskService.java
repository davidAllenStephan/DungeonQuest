package davidmarino.map.mapservice;

import davidmarino.map.mapmodels.Polygon;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Class {@code CoastMask}
 * @author David Marino
 * @version 14 Jun 2025
 */
@Service
public class CoastMaskService {

    /**
     * Finds the coast site points by checking that a polygon has at least one neighbor that is seaLevel and not all
     * neighbors are at seaLevel (isolated sea tile)
     * @param polygons
     * @return {@code ArrayList<Polygon>}
     */
    public ArrayList<Polygon> getCoastMask(ArrayList<Polygon> polygons, double waterLevel) {
        ArrayList<Polygon> coastMask = new ArrayList<>();
        for (Polygon polygon : polygons) {
            int seaNeighborCount = 0;
            if (polygon.site.z >= waterLevel) {
                for (Polygon neighbor : polygon.neighbors) {
                    if (neighbor.site.z < waterLevel) {
                        seaNeighborCount++;
                    }
                }
                if (0 < seaNeighborCount && seaNeighborCount < polygon.neighbors.size()) {
                    coastMask.add(polygon);
                }
            }
        }
        return coastMask;
    }

    /**
     * Turns land to sea randomly in a inward direction.
     * @param coastMask holds the polygons that are on the coast
     * @param startPercent is the chance for erosion to start
     * @param spreadChance is the chance for an eroding tile to spread
     * @param maxChunks is the max number of erosion start events
     * @param maxStepsPerChunk is the maximum number of tiles to change to sea
     */
    public void erodeInwardFromRandomCoastPoints(ArrayList<Polygon> coastMask, double startPercent, double spreadChance, int maxChunks, int maxStepsPerChunk, double waterLevel) {
        Random r = new Random();
        Set<Polygon> globalVisited = new HashSet<>();
        Collections.shuffle(coastMask);
        int numStarts = Math.min((int)(coastMask.size() * startPercent), maxChunks);
        for (int i = 0; i < numStarts; i++) {
            Polygon start = coastMask.get(i);
            if (globalVisited.contains(start)) continue;
            Queue<Polygon> queue = new LinkedList<>();
            Set<Polygon> localVisited = new HashSet<>();
            queue.add(start);
            localVisited.add(start);
            globalVisited.add(start);
            int steps = 0;
            while (!queue.isEmpty() && steps < maxStepsPerChunk) {
                Polygon current = queue.poll();
                current.site.z = waterLevel - 0.001; // mark as sea
                for (Polygon neighbor : current.neighbors) {
                    if (neighbor.site.z >= waterLevel && // it's land
                            !localVisited.contains(neighbor)) {
                        if (r.nextDouble() < spreadChance) {
                            queue.add(neighbor);
                            localVisited.add(neighbor);
                            globalVisited.add(neighbor);
                        }
                    }
                }
                steps++;
            }
        }
    }

    public void degenerateCoast(ArrayList<Polygon> voronoiPolygons, double waterLevel, double startPercent, double spreadChance, int maxChunks, int maxStepsPerChunk, double coastLevel) {
        ArrayList<Polygon> coastMask = getCoastMask(voronoiPolygons, waterLevel);
        erodeInwardFromRandomCoastPoints(coastMask, startPercent, spreadChance, maxChunks, maxStepsPerChunk, waterLevel);
        ArrayList<Polygon> coast = getCoastMask(voronoiPolygons, waterLevel);
        Random r = new Random();
        for (Polygon polygon : coast) {
            polygon.site.z = r.nextDouble(waterLevel, coastLevel);
        }
    }
}
