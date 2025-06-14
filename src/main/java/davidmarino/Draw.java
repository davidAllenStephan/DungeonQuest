package davidmarino;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The {@code Draw} class is used to set up and render all objects.
 * @version 13 Jun 2025
 * @author David Marino
 */

public class Draw {

    /**
     * Renders a point.
     * @param g2 is the render library
     * @param x 0 <= x < image width
     * @param y 0 <= y < image height
     */
    private void drawPoint(Graphics2D g2, int x, int y) {
        g2.setColor(Color.BLACK);
        g2.fillOval(x - (Parameters.vertexSize / 2), y - (Parameters.vertexSize / 2), Parameters.vertexSize, Parameters.vertexSize);
    }

    /**
     * Renders a polygon.
     * @param g2 is the render library
     */
    private void drawPolygon(Graphics2D g2, Polygon polygon, Color color) {
        int n = polygon.vertices.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        for (int i = 0; i < n; i++) {
            xPoints[i] = (int) Math.round(polygon.vertices.get(i).x);
            yPoints[i] = (int) Math.round(polygon.vertices.get(i).y);
        }

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(Parameters.edgeSize));
        g2.drawPolygon(xPoints, yPoints, n);

        g2.setColor(color);
        g2.fillPolygon(xPoints, yPoints, n);
    }

    /**
     * Executes the start of the generation.
     */
    public void run() {
        BufferedImage image = new BufferedImage(Parameters.width, Parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        // Background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, Parameters.width, Parameters.height);

        ArrayList<Point> points = Point.generateRandomPoints(Parameters.numberOfPoints);
        for (int i = 0; i < Parameters.maxLloydIterations; i++) {
            ArrayList<Point> newPoints = new ArrayList<>();
            for (Point point : points) {
                Polygon p = Polygon.computeVoronoiCell(point, points);
                newPoints.add(LloydRelaxation.findCentroid(p, point));
            }
            points = newPoints;
        }

        for (Point point : points) {
            Polygon p = Polygon.computeVoronoiCell(point, points);
            if (point.z >= Parameters.waterLevel) {
                drawPolygon(g2, p, new Color(0, (int) (point.z * 255), 0));
            } else {
                drawPolygon(g2, p, new Color(0, 0, (int) (point.z * 255)));
            }
        }
        for (Point p : points) {
            drawPoint(g2, (int) p.x, (int) p.y);
        }
        g2.dispose();
        ImageExporter.exportToPNG(image);
    }
}
