package davidmarino;

import java.awt.*;
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

    public boolean isVertical = false;

    /**
     * Constructs {@code Line} using point A and B.
     * @param A point
     * @param B point
     */
    public Line(Point A, Point B) {
        this.A = A;
        this.B = B;
        if (B.x != A.x) {
            m = (B.y - A.y) / (B.x - A.x);
            b = A.y - m * A.x;
        } else {
            m = Double.POSITIVE_INFINITY;
            b = Double.NaN;
            isVertical = true;
        }
    }

    /**
     * Line copy constructor
     * @param A point
     * @param B point
     * @param m is slope
     * @param b is y index
     */
    public Line(Point A, Point B, double m, double b, boolean isVertical) {
        this.A = A;
        this.B = B;
        this.m = m;
        this.b = b;
        this.isVertical = isVertical;
    }

    /**
     * Finds the bisector line of this line and returns it as a new line.
     * @return {@code Line}
     */
    public Line findBisector() {
        double midX = (A.x + B.x) / 2;
        double midY = (A.y + B.y) / 2;
        double pM;
        if (m == 0) {
            pM = Double.POSITIVE_INFINITY;
        } else if (Double.isInfinite(m)) {
            pM = 0;
        } else {
            pM = -1.0 / m;
        }
        if (Double.isInfinite(pM)) {
            return new Line(new Point(midX, 0), new Point(midX, Parameters.height), pM, Double.NaN, true);
        }
        Line l = new Line(this.A, this.B, pM, midY - (pM * midX), false);
        int width = Parameters.width;
        int height = Parameters.height;
        ArrayList<Point> validPoints = new ArrayList<>();
        double y0 = l.m * 0.0 + l.b;
        if (y0 >= 0 && y0 <= height) {
            validPoints.add(new Point(0, y0));
        }
        double yWidth = l.m * width + l.b;
        if (yWidth >= 0 && yWidth <= height) {
            validPoints.add(new Point(width, yWidth));
        }
        if (l.m != 0) {
            double x0 = (0.0 - l.b) / l.m;
            if (x0 >= 0 && x0 <= width) {
                validPoints.add(new Point(x0, 0));
            }
        }
        if (l.m != 0) {
            double xHeight = (height - l.b) / l.m;
            if (xHeight >= 0 && xHeight <= width) {
                validPoints.add(new Point(xHeight, height));
            }
        }
        return new Line(validPoints.get(0), validPoints.get(1));
    }

    /**
     * Draws line using render library Graphics2D.
     * @param g2 is the render library
     * @param line to be rendered
     * @param color of the line
     */
    public static void drawLine(Graphics2D g2, Line line, Color color) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(Parameters.edgeSize));
        g2.drawLine((int) line.A.x, (int) line.A.y, (int) line.B.x, (int) line.B.y);
    }

    @Override
    public String toString() {
        return "Line [A=" + A + ", B=" + B + ", m=" + m + ", b=" + b + "]";
    }
}