package davidmarino;

import java.util.ArrayList;
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
     * Copy constructor
     * @param x
     * @param y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

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

    public static ArrayList<Point> generateRandomPoints(int count) {
        ArrayList<Point> points = new ArrayList<>();
        Random random = new Random();
        while (count > 0) {
            int x = random.nextInt(0, Parameters.width);
            int y = random.nextInt(0, Parameters.height);
            if (!Point.withinBounds(points, x, y, 100)) {
                points.add(new Point(x, y, PerlinNoise.getNoise(x * 0.1, y * 0.1)));
                count--;
            }
        }
        return points;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
