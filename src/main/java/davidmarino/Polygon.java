package davidmarino;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class {@code Polygon} used to create a voronoi diagram. This diagram creates natural looking polygons that can be
 * used to render maps. For more details about voronoi diagrams look here <A href="https://en.wikipedia.org/wiki/Voronoi_diagram">https://en.wikipedia.org/wiki/Voronoi_diagram</A>
 * @author David Marino
 * @version 13 Jun 2025
 */
public class Polygon {

    /**
     * Vertices of the polygon.
     * 2 < ∣S∣ < ∞
     */
    public ArrayList<Point> vertices;
    /**
     * Polygons that share an edge with this polygon
     */
    public HashSet<Polygon> neighbors;
    /**
     * The point that this polygon orientates itself
     */
    public Point site;


    /**
     * Polygon constructor with no neighbors.
     * @param vertices are the points of the polygon
     * @param site is the point this polygon is orientated
     */
    public Polygon(ArrayList<Point> vertices, Point site) {
        this.vertices = vertices;
        this.neighbors = new HashSet<>();
        this.site = site;
    }

    /**
     * Polygon constructor with no neighbors or site.
     * Used when doing calculations where site is not needed such as when creating noisy borders.
     * @param vertices are the points of the polygon
     */
    public Polygon(ArrayList<Point> vertices) {
        this.vertices = vertices;
        this.neighbors = new HashSet<>();
        this.site = null;
    }

    /**
     * Polygon copy constructor
     * @param vertices are the points of the polygon
     * @param neighbors are the polygons that this polygon shares a border with
     * @param site is the point this polygon is orientated
     */
    public Polygon(ArrayList<Point> vertices, HashSet<Polygon> neighbors, Point site) {
        this.vertices = vertices;
        this.neighbors = neighbors;
        this.site = site;
    }

    /**
     * Generates the initial polygon that is the size of the image.
     * @param width of the image
     * @param height of the image
     * @return {@code Polygon}
     */
    public static Polygon getBoundingBox(int width, int height) {
        ArrayList<Point> box = new ArrayList<>();
        box.add(new Point(0, 0));
        box.add(new Point(width, 0));
        box.add(new Point(width, height));
        box.add(new Point(0, height));
        return new Polygon(box, null);
    }

    /**
     * Generates a voronoi cell. First creates the initial bounding box polygon.
     * Next clips the polygon based on the bisector of this polygons site and all other sites.
     * @param site of the vertex being generated
     * @param allSites is a list of all sites that exist
     * @return {@code Polygon}
     */
    public static Polygon computeVoronoiCell(Point site, ArrayList<Point> allSites) {
        Polygon cell = getBoundingBox(Parameters.width, Parameters.height);
        cell.site = site;
        for (Point other : allSites) {
            if (other == site) continue;
            Line bisector = new Line(site, other).findBisector();
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
    public ArrayList<Polygon> findQuadrilaterals() {
        ArrayList<Polygon> quadrilaterals = new ArrayList<>();
        for (Polygon neighbor : neighbors) {
            ArrayList<Point> shared = new ArrayList<>();
            for (Point p : vertices) {
                if (neighbor.vertices.contains(p)) {
                    shared.add(p);
                }
            }
            if (shared.size() == 2) {
                ArrayList<Point> quadrilateral = new ArrayList<>();
                quadrilateral.add(site);
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
            Point curr = points.get(i);
            Point next = points.get((i + 1) % points.size());
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
    private Polygon findNeighborBySite(Point neighborSite) {
        for (Polygon neighbor : neighbors) {
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
    public void applyNoisyBorder(double diversion) {
        ArrayList<Polygon> quads = findQuadrilaterals();
        for (Polygon quad : quads) {
            Point siteA = quad.vertices.get(0);
            Point shared0 = quad.vertices.get(1);
            Point siteB = quad.vertices.get(2);
            Point shared1 = quad.vertices.get(3);
            Point noisyPoint = Point.interpolate(siteA, siteB, diversion);
            insertBetweenVertices(this.vertices, shared0, shared1, noisyPoint);
            Polygon neighbor = findNeighborBySite(siteB);
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
    public static ArrayList<Polygon> generateVoronoiPolygons(ArrayList<Point> sitePoints) {
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (Point point : sitePoints) {
            Polygon poly = computeVoronoiCell(point, sitePoints);
            polygons.add(poly);
        }
        return polygons;
    }

    /**
     * Finds neighbors of all polygons.
     * @param polygons to match as neighbors
     */
    public static void findNeighbors(ArrayList<Polygon> polygons) {
        HashMap<Point, HashSet<Polygon>> vertexToPolygonsMap = new HashMap<>();
        for (Polygon polygon : polygons) {
            for (Point vertex : polygon.vertices) {
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

    /**
     * Renders a polygon.
     * @param g2 is the render library
     * @param polygon to be rendered
     * @param color of the filled polygon
     */
    public static void drawFilledPolygon(Graphics2D g2, Polygon polygon, Color color) {
        int n = polygon.vertices.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];
        for (int i = 0; i < n; i++) {
            xPoints[i] = (int) Math.round(polygon.vertices.get(i).x);
            yPoints[i] = (int) Math.round(polygon.vertices.get(i).y);
        }
        g2.setColor(color);
        g2.fillPolygon(xPoints, yPoints, n);
    }

    /**
     * Renders polygons border
     * @param g2 is the render library
     * @param polygon to be rendered
     * @param color of the polygon border
     */
    public static void drawPolygonBorder(Graphics2D g2, Polygon polygon, Color color) {
        int n = polygon.vertices.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];
        for (int i = 0; i < n; i++) {
            xPoints[i] = (int) Math.round(polygon.vertices.get(i).x);
            yPoints[i] = (int) Math.round(polygon.vertices.get(i).y);
        }
        g2.setColor(color);
        g2.setStroke(new BasicStroke(Parameters.edgeSize));
        g2.drawPolygon(xPoints, yPoints, n);

    }

    /**
     * Renders set of filled polygons.
     * Colors based on z value of Polygon.
     * @param g2 is the render library
     * @param polygons to be rendered
     */
    public static void drawFilledPolygons(Graphics2D g2, ArrayList<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            if (polygon.site.z < Parameters.waterLevel) {
                drawFilledPolygon(g2, polygon, new Color(0, 0, (int) (polygon.site.z * 255)));
            } else if (polygon.site.z < Parameters.coastLevel) {
                drawFilledPolygon(g2, polygon, new Color((int) (polygon.site.z * 255) * 2, (int) (polygon.site.z * 255) * 2, 0));
            } else if (polygon.site.z < Parameters.whiteCapLevel) {
                drawFilledPolygon(g2, polygon, new Color(0, (int) (polygon.site.z * 255), 0));
            } else {
                drawFilledPolygon(g2, polygon, new Color((int) (polygon.site.z * 255), (int) (polygon.site.z * 255), (int) (polygon.site.z * 255)));
            }
        }
    }

    /**
     * Renders set of polygon boarders.
     * @param g2 is the render library
     * @param polygons to be rendered
     * @param color of the polygon boarder
     */
    public static void drawPolygonBorders(Graphics2D g2, ArrayList<Polygon> polygons, Color color) {
        for (Polygon polygon : polygons) {
            drawPolygonBorder(g2, polygon, color);
        }
    }

    @Override
    public String toString() {
        return vertices.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Polygon)) return false;
        return site.equals(((Polygon) obj).site);
    }

}

