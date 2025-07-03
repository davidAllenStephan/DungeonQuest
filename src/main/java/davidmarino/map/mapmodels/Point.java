package davidmarino.map.mapmodels;

import lombok.Data;

import java.util.Objects;

/**
 * The {@code Point} class represents a point in euclidean space.
 * @author David Marino
 * @version 13 Jun 2025
 */
@Data
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
