package davidmarino;

import java.util.ArrayList;

/**
 * Class {@code Line} models a segment and exists in euclidean space.
 * @author David Marino
 * @version 13 Jun 2025
 */

public class Line {
    public Point A;
    public Point B;

    /**
     * The slope of segment AB.
     */
    public double m;

    /**
     * The y index of segment AB.
     */
    public double b;

    /**
     * Constructs {@code Line} using point A and B.
     * @param A point
     * @param B point
     */
    public Line(Point A, Point B) {
        this.A = A;
        this.B = B;
        this.m = (B.y - A.y) / (B.x - A.x);
        this.b = A.y - this.m * A.x;
    }

    /**
     * Copy Constructor
     * @param A point
     * @param B point
     * @param m is slope
     * @param b is y index
     */
    public Line(Point A, Point B, double m, double b) {
        this.A = A;
        this.B = B;
        this.m = m;
        this.b = b;
    }

    /**
     * Finds the bisector line of this object.
     * @return {@code Line}
     */
    public Line findBisector() {
        double midX = (A.x + B.x) / 2;
        double midY = (A.y + B.y) / 2;
        Line l = new Line(this.A, this.B, (-1.0 / this.m), (midY - ((-1.0 / this.m) * midX)));
        int width = Parameters.width;
        int height = Parameters.height;
        ArrayList<Point> validPoints = new ArrayList<>();

        // Intersect with left (x = 0)
        double y0 = l.m * 0.0 + l.b;
        if (y0 >= 0 && y0 <= height) {
            validPoints.add(new Point(0, y0));
        }

        // Intersect with right (x = width)
        double yWidth = l.m * width + l.b;
        if (yWidth >= 0 && yWidth <= height) {
            validPoints.add(new Point(width, yWidth));
        }

        // Intersect with top (y = 0)
        if (l.m != 0) {
            double x0 = (0.0 - l.b) / l.m;
            if (x0 >= 0 && x0 <= width) {
                validPoints.add(new Point(x0, 0));
            }
        }

        // Intersect with bottom (y = height)
        if (l.m != 0) {
            double xHeight = (height - l.b) / l.m;
            if (xHeight >= 0 && xHeight <= width) {
                validPoints.add(new Point(xHeight, height));
            }
        }

        return new Line(validPoints.get(0), validPoints.get(1));
    }
}