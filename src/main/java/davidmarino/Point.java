package davidmarino;

import java.util.ArrayList;

/**
 * The {@code Point} class represents a point in euclidean space.
 * @author David Marino
 * @version 13 Jun 2025
 */

public class Point {
    public double x;
    public double y;

    /**
     * Copy constructor
     * @param x
     * @param y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if a point is within a certain distance of another point.
     * @param points is a list of all points
     * @param x
     * @param y
     * @param space is the set distance between points
     * @return {@code boolean}
     */
    public static boolean withinBounds(ArrayList<Point> points, int x, int y, int space) {
        if (points.isEmpty()) return false;
        for (Point p : points) {
            boolean res = ((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y)) < (space * space);
            if (res) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
