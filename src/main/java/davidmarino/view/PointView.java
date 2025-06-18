package davidmarino.view;

import davidmarino.Parameters;
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
    public static void drawPoints(Graphics2D g2, ArrayList<Point> points, Color color) {
        for (Point point : points) {
            drawPoint(g2, (int) point.x, (int) point.y, color);
        }
    }

    /**
     * Renders a point.
     * @param g2 is the render library
     * @param x
     * @param y
     */
    private static void drawPoint(Graphics2D g2, int x, int y, Color color) {
        g2.setColor(color);
        int size = Parameters.vertexSize;
        g2.fillOval(x - (size / 2), y - (size / 2), size, size);
    }
}
