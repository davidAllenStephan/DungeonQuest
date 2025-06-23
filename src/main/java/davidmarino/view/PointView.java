package davidmarino.view;

import davidmarino.model.Point;

import java.awt.*;
import java.util.ArrayList;

public class PointView {

    /**
     * Renders a set of points.
     * @param g2 is the render library
     * @param points to be rendered
     * @param color of the points
     */
    public static void drawPoints(Graphics2D g2, ArrayList<Point> points, int vertexRadius, Color color) {
        for (Point point : points) {
            drawPoint(g2, point, vertexRadius, color);
        }
    }

    /**
     * Renders a point.
     * @param g2 is the render library
     */
    public static void drawPoint(Graphics2D g2, Point p, int vertexRadius, Color color) {
        g2.setColor(color);
        g2.fillOval((int) (p.x - (double) (vertexRadius / 2)), (int) (p.y - (double) (vertexRadius / 2)), vertexRadius, vertexRadius);
    }
}
