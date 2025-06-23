package davidmarino.view;

import davidmarino.model.Line;

import java.awt.*;

public class LineView {
    /**
     * Draws line using render library Graphics2D.
     * @param g2 is the render library
     * @param line to be rendered
     * @param color of the line
     */
    public static void drawLine(Graphics2D g2, Line line, int edgeWeight, Color color) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(edgeWeight));
        g2.drawLine((int) line.A.x, (int) line.A.y, (int) line.B.x, (int) line.B.y);
    }
}
