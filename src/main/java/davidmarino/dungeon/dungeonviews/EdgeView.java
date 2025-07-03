package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.Edge;

import java.awt.*;
import java.util.Set;

public class EdgeView {
    public static void drawEdge(Graphics2D g2, Edge edge, int edgeWeight, Color color) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(edgeWeight));
        int x1 = (int) edge.from.center.x;
        int y1 = (int) edge.from.center.y;
        int x2 = (int) edge.to.center.x;
        int y2 = (int) edge.to.center.y;
        if (x1 == x2 || y1 == y2) {
            g2.drawLine(x1, y1, x2, y2);
        } else {
            int midX = x2;
            int midY = y1;
            g2.drawLine(x1, y1, midX, midY);
            g2.drawLine(midX, midY, x2, y2);
        }
    }

    public static void drawEdges(Graphics2D g2, Set<Edge> edge, int edgeWeight, Color color) {
        for (Edge e : edge) {
            drawEdge(g2, e, edgeWeight, color);
        }
    }
}
