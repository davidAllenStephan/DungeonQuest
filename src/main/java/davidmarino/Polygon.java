package davidmarino;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class {@code Polygon} goal is to create a voronoi diagram. This diagram creates natural looking polygons that can be
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
    public HashSet<Polygon> neighbors;
    public Point site;

    /**
     * Contracts a Polygon list of vertices.
     */
    public Polygon(ArrayList<Point> vertices, Point site) {
        this.vertices = vertices;
        this.neighbors = new HashSet<>();
        this.site = site;
    }

    public Polygon(ArrayList<Point> vertices) {
        this.vertices = vertices;
        this.neighbors = new HashSet<>();
        this.site = null;
    }

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
     * Generates the points of a voronoi cell.
     * @param site of the vertex being generated
     * @param allSites is a list of all sites that exist
     * @return {@code ArrayList<Point>}
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

    private void insertBetweenVertices(ArrayList<Point> verts, Point a, Point b, Point insertPoint) {
        for (int i = 0; i < verts.size(); i++) {
            Point curr = verts.get(i);
            Point next = verts.get((i + 1) % verts.size());

            if ((curr.equals(a) && next.equals(b)) || (curr.equals(b) && next.equals(a))) {
                verts.add(i + 1, insertPoint);
                return;
            }
        }
    }

    private Polygon findNeighborBySite(Point neighborSite) {
        for (Polygon neighbor : neighbors) {
            if (neighbor.site.equals(neighborSite)) {
                return neighbor;
            }
        }
        return null;
    }

    public Point subdivideByDiversion(double diversion) {
        Point site = vertices.get(0);
        Point shared0 = vertices.get(1);
        Point neighbor = vertices.get(2);
        Point shared1 = vertices.get(3);

        // One of these points needs to be returned
        Point p1 = interpolate(site, neighbor, diversion);    // diag: site -> neighbor
        return p1;
//        Point p2 = interpolate(site, shared0, diversion);      // edge: site -> shared0
//        Point p3 = interpolate(site, shared1, diversion);      // edge: site -> shared1
//        Point p4 = interpolate(neighbor, shared0, diversion);  // edge: neighbor -> shared0
//        Point p5 = interpolate(neighbor, shared1, diversion);  // edge: neighbor -> shared1
//
//        return subdividedPolygons;
    }

    private Point interpolate(Point a, Point b, double t) {
        double x = a.x + t * (b.x - a.x);
        double y = a.y + t * (b.y - a.y);
        return new Point(x, y);
    }

    public static ArrayList<Polygon> inverseQuadrilateral(Polygon quadrilateral, ArrayList<Polygon> subdivisions) {
        ArrayList<Polygon> inverseQuadrilaterals = new ArrayList<>();
        ArrayList<Point> inverseQuad1Points = new ArrayList<>();
        inverseQuad1Points.add(quadrilateral.vertices.get(1));
        inverseQuad1Points.add(subdivisions.get(0).vertices.get(1));
        inverseQuad1Points.add(subdivisions.get(0).vertices.get(2));
        inverseQuad1Points.add(subdivisions.get(1).vertices.get(1));
        Polygon i1 = new Polygon(inverseQuad1Points);
        inverseQuadrilaterals.add(i1);

        ArrayList<Point> inverseQuad2Points = new ArrayList<>();
        inverseQuad2Points.add(quadrilateral.vertices.get(3));
        inverseQuad2Points.add(subdivisions.get(0).vertices.get(3));
        inverseQuad2Points.add(subdivisions.get(0).vertices.get(2));
        inverseQuad2Points.add(subdivisions.get(1).vertices.get(3));
        Polygon i2 = new Polygon(inverseQuad2Points);
        inverseQuadrilaterals.add(i2);

        return inverseQuadrilaterals;
    }

    /**
     * Adds a new point to this polygon and to all neighbors.
     * The new point is the result of the midpoint displacement to add noise to the borderlines.
     */
    public void applyNoisyBorder(double diversion) {
        ArrayList<Polygon> quads = findQuadrilaterals();

        for (Polygon quad : quads) {
            Point siteA = quad.vertices.get(0);
            Point shared0 = quad.vertices.get(1);
            Point siteB = quad.vertices.get(2);
            Point shared1 = quad.vertices.get(3);

            // Generate noisy midpoint
            Point noisyPoint = interpolate(siteA, siteB, diversion);

            // Update this polygon
            insertBetweenVertices(this.vertices, shared0, shared1, noisyPoint);

            // Find the neighbor polygon
            Polygon neighbor = findNeighborBySite(siteB);
            if (neighbor != null) {
                insertBetweenVertices(neighbor.vertices, shared0, shared1, noisyPoint);
            }
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

