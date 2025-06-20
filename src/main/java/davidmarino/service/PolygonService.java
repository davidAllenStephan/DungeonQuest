package davidmarino.service;

import davidmarino.model.Line;
import davidmarino.model.Point;
import davidmarino.model.Polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PolygonService {

    /**
     * Generates the initial polygon that is the size of the image.
     * @param width of the image
     * @param height of the image
     * @return {@code Polygon}
     */
    private Polygon getBoundingBox(int width, int height) {
        ArrayList<Point> box = new ArrayList<>();
        box.add(new davidmarino.model.Point(0, 0));
        box.add(new davidmarino.model.Point(width, 0));
        box.add(new davidmarino.model.Point(width, height));
        box.add(new davidmarino.model.Point(0, height));
        return new Polygon(box, null);
    }

    /**
     * Generates a voronoi cell. First creates the initial bounding box polygon.
     * Next clips the polygon based on the bisector of this polygons site and all other sites.
     * @param site of the vertex being generated
     * @param allSites is a list of all sites that exist
     * @return {@code Polygon}
     */
    public Polygon computeVoronoiCell(Point site, ArrayList<Point> allSites, int width, int height) {
        Polygon cell = getBoundingBox(width, height);
        cell.site = site;
        for (davidmarino.model.Point other : allSites) {
            if (other == site) continue;
            LineService lc = new LineService();
            Line bisector = lc.findBisector(new Line(site, other), width, height);
            cell = GeometryUtils.clipPolygon(cell, bisector, site);
        }
        return cell;
    }

    /**
     * Finds the quadrilaterals that exists between this polygon and its neighbors.
     * The quadrilaterals are made of this site and the current neighbor and their intersection points.
     * The number of quadrilaterals found will always be the size of the number of neighbors a polygon has.
     * @return {@code ArrayList<Polygon>}
     */
    public ArrayList<Polygon> findQuadrilaterals(Polygon polygon) {
        ArrayList<Polygon> quadrilaterals = new ArrayList<>();
        for (Polygon neighbor : polygon.neighbors) {
            ArrayList<Point> shared = new ArrayList<>();
            for (Point p : polygon.vertices) {
                if (neighbor.vertices.contains(p)) {
                    shared.add(p);
                }
            }
            if (shared.size() == 2) {
                ArrayList<Point> quadrilateral = new ArrayList<>();
                quadrilateral.add(polygon.site);
                quadrilateral.add(shared.get(0));
                quadrilateral.add(neighbor.site);
                quadrilateral.add(shared.get(1));
                quadrilaterals.add(new Polygon(quadrilateral));
            }
        }
        return quadrilaterals;
    }

    /**
     * Determines where to insert new point to keep a valid polygon shape.
     * @param points
     * @param a
     * @param b
     * @param insertPoint
     */
    private void insertBetweenVertices(ArrayList<Point> points, Point a, Point b, Point insertPoint) {
        for (int i = 0; i < points.size(); i++) {
            davidmarino.model.Point curr = points.get(i);
            davidmarino.model.Point next = points.get((i + 1) % points.size());
            if ((curr.equals(a) && next.equals(b)) || (curr.equals(b) && next.equals(a))) {
                points.add(i + 1, insertPoint);
                return;
            }
        }
    }

    /**
     * Finds the neighbor polygon that exists in this polygon based on the neighbors site.
     * @param neighborSite is the site that belongs to a neighbor of this polygon
     * @return {@code Polygon}
     */
    private Polygon findNeighborBySite(Polygon polygon, Point neighborSite) {
        for (Polygon neighbor : polygon.neighbors) {
            if (neighbor.site.equals(neighborSite)) {
                return neighbor;
            }
        }
        return null;
    }

    /**
     * Adds a new point to this polygon and to all neighbors.
     * The new point is the result of the midpoint displacement to add noise to the borderlines.
     * @param diversion is the distance to skew the line from 0 to 1
     */
    public void applyNoisyBorder(Polygon polygon, double diversion) {
        ArrayList<Polygon> quads = findQuadrilaterals(polygon);
        for (Polygon quad : quads) {
            Point siteA = quad.vertices.get(0);
            Point shared0 = quad.vertices.get(1);
            Point siteB = quad.vertices.get(2);
            Point shared1 = quad.vertices.get(3);
            Point noisyPoint = Point.interpolate(siteA, siteB, diversion);
            insertBetweenVertices(polygon.vertices, shared0, shared1, noisyPoint);
            Polygon neighbor = findNeighborBySite(polygon, siteB);
            if (neighbor != null) {
                insertBetweenVertices(neighbor.vertices, shared0, shared1, noisyPoint);
            }
        }
    }

    /**
     * Generates voronoi polygons given the site points.
     * @param sitePoints to determine Voronoi polygon
     * @return {@code ArrayList<Polygon>}
     */
    public ArrayList<Polygon> generateVoronoiPolygons(ArrayList<Point> sitePoints, int width, int height) {
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (Point point : sitePoints) {
            Polygon poly = computeVoronoiCell(point, sitePoints, width, height);
            polygons.add(poly);
        }
        return polygons;
    }

    /**
     * Finds neighbors of all polygons.
     * @param polygons to match as neighbors
     */
    public void findNeighbors(ArrayList<Polygon> polygons) {
        HashMap<Point, HashSet<Polygon>> vertexToPolygonsMap = new HashMap<>();
        for (Polygon polygon : polygons) {
            for (davidmarino.model.Point vertex : polygon.vertices) {
                vertexToPolygonsMap
                        .computeIfAbsent(vertex, k -> new HashSet<>())
                        .add(polygon);
            }
        }
        for (Polygon polygon : polygons) {
            for (Point vertex : polygon.vertices) {
                HashSet<Polygon> adjacentPolygons = vertexToPolygonsMap.get(vertex);
                if (adjacentPolygons != null) {
                    for (Polygon neighbor : adjacentPolygons) {
                        if (neighbor != polygon) {
                            polygon.neighbors.add(neighbor);
                        }
                    }
                }
            }
        }
    }
}
