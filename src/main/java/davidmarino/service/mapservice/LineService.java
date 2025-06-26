package davidmarino.service.mapservice;

import davidmarino.model.mapmodels.Line;
import davidmarino.model.mapmodels.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LineService {
    /**
     * Finds the bisector line of this line and returns it as a new line.
     * @return {@code Line}
     */
    public Line findBisector(Line line, int width, int height) {
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
            return new Line(new Point(midX, 0), new Point(midX, height), pM, Double.NaN, true);
        }
        Line l = new Line(line.A, line.B, pM, midY - (pM * midX), false);
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

    public static boolean isBelow(Line line, Point point) {
        double res = (line.m * point.x) + line.b;
        return res > point.y;
    }

    public static Point intersect(Line A, Line B) {
        if (A.m == B.m) {
            return null;
        }
        double x = (B.b - A.b) / (A.m - B.m);
        double y = A.m * x + A.b;
        Point intersection = new Point(x, y);
        return intersection;

    }

}
