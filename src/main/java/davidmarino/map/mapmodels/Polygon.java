package davidmarino.map.mapmodels;

import java.util.ArrayList;
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

