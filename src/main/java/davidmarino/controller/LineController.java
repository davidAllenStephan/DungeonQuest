package davidmarino.controller;

import davidmarino.Parameters;
import davidmarino.model.Line;
import davidmarino.model.Point;

import java.util.ArrayList;

public class LineController {
    /**
     * Finds the bisector line of this line and returns it as a new line.
     * @return {@code Line}
     */
    public Line findBisector(Line line) {
        double midX = (line.A.x + line.B.x) / 2;
        double midY = (line.A.y + line.B.y) / 2;
        double pM;
        if (line.m == 0) {
            pM = Double.POSITIVE_INFINITY;
        } else if (Double.isInfinite(line.m)) {
            pM = 0;
        } else {
            pM = -1.0 / line.m;
        }
        if (Double.isInfinite(pM)) {
            return new Line(new davidmarino.model.Point(midX, 0), new davidmarino.model.Point(midX, Parameters.height), pM, Double.NaN, true);
        }
        Line l = new Line(line.A, line.B, pM, midY - (pM * midX), false);
        int width = Parameters.width;
        int height = Parameters.height;
        ArrayList<Point> validPoints = new ArrayList<>();
        double y0 = l.m * 0.0 + l.b;
        if (y0 >= 0 && y0 <= height) {
            validPoints.add(new davidmarino.model.Point(0, y0));
        }
        double yWidth = l.m * width + l.b;
        if (yWidth >= 0 && yWidth <= height) {
            validPoints.add(new davidmarino.model.Point(width, yWidth));
        }
        if (l.m != 0) {
            double x0 = (0.0 - l.b) / l.m;
            if (x0 >= 0 && x0 <= width) {
                validPoints.add(new davidmarino.model.Point(x0, 0));
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
}
