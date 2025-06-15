package davidmarino;

import java.util.ArrayList;
import java.util.HashSet;

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

    @Override
    public String toString() {
        return vertices.toString();
    }

}

