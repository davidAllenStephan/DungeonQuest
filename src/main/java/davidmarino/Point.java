package davidmarino;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * The {@code Point} class represents a point in euclidean space.
 * @author David Marino
 * @version 13 Jun 2025
 */
public class Point {
    public double x;
    public double y;
    public double z;

    /**
     * Point constructor not including z
     * @param x
     * @param y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Point copy constructor
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    /**
     * Creates random points dispersed within the domain and range of the image.
     * @param count of the points created
     * @return {@code ArrayList<Point>}
     */
    public static ArrayList<Point> generateRandomPoints(int count) {
        ArrayList<Point> points = new ArrayList<>();
        Random random = new Random();
        while (count > 0) {
            int x = random.nextInt(0, Parameters.width);
            int y = random.nextInt(0, Parameters.height);
            if (!Point.withinBounds(points, x, y, 100)) {
                points.add(new Point(x, y));
                count--;
            }
        }
        return points;
    }

    /**
     * Calculates a point between a and b based on value t.
     * @param a point
     * @param b point
     * @param t value ranging 0 to 1
     * @return {@code Point}
     */
    public static Point interpolate(Point a, Point b, double t) {
        double x = a.x + t * (b.x - a.x);
        double y = a.y + t * (b.y - a.y);
        return new Point(x, y);
    }

    /**
     * Renders a point.
     * @param g2 is the render library
     * @param x
     * @param y
     */
    private static void drawPoint(Graphics2D g2, int x, int y, Color color) {
        g2.setColor(color);
        g2.fillOval(x - (Parameters.vertexSize / 2), y - (Parameters.vertexSize / 2), Parameters.vertexSize, Parameters.vertexSize);
    }

    /**
     * Renders a set of points.
     * @param g2 is the render library
     * @param points to be rendered
     * @param color of the points
     */
    public static void drawPoints(Graphics2D g2, ArrayList<Point> points, Color color) {
        for (Point point : points) {
            drawPoint(g2, (int) point.x, (int) point.y, color);
        }
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Value to account for rounding errors.
     */
    private static final double EPSILON = 1e-9;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return Math.abs(x - other.x) < EPSILON &&
                Math.abs(y - other.y) < EPSILON &&
                Math.abs(z - other.z) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                (int)(x / EPSILON),
                (int)(y / EPSILON),
                (int)(z / EPSILON)
        );
    }
}
