package davidmarino.model;

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
    public Line(davidmarino.model.Point A, davidmarino.model.Point B, double m, double b, boolean isVertical) {
        this.A = A;
        this.B = B;
        this.m = m;
        this.b = b;
        this.isVertical = isVertical;
    }

    @Override
    public String toString() {
        return "Line [A=" + A + ", B=" + B + ", m=" + m + ", b=" + b + "]";
    }
}