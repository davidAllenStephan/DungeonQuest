package davidmarino;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

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
        g2.setColor(new Color(233, 196, 106));
        g2.fillOval(x - (Parameters.vertexSize / 2), y - (Parameters.vertexSize / 2), Parameters.vertexSize, Parameters.vertexSize);
    }

    /**
     * Renders a polygon.
     * @param g2 is the render library
     * @param points collection of vertices
     */
    private void drawPolygon(Graphics2D g2, ArrayList<Point> points) {
        int n = points.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        for (int i = 0; i < n; i++) {
            xPoints[i] = (int) Math.round(points.get(i).x);
            yPoints[i] = (int) Math.round(points.get(i).y);
        }

        g2.setColor(new Color(38, 70, 83));
        g2.setStroke(new BasicStroke(Parameters.edgeSize));
        g2.drawPolygon(xPoints, yPoints, n);
        g2.setColor(new Color(42, 157, 143));
        g2.fillPolygon(xPoints, yPoints, n);
    }

    /**
     * Executes the start of the generation.
     */
    public void run() {
        BufferedImage image = new BufferedImage(Parameters.width, Parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        ArrayList<Point> points = new ArrayList<>();

        // Background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, Parameters.width, Parameters.height);

        Random random = new Random();
        int maxPoints = Parameters.numberOfPoints;
        int curPoints = 0;

        // Just adding points
        while (curPoints < maxPoints) {
            int localXPoint = random.nextInt(0, Parameters.width);
            int localYPoint = random.nextInt(0, Parameters.height);
            if (!Point.withinBounds(points, localXPoint, localYPoint, 100)) {
                points.add(new Point(localXPoint, localYPoint));
                curPoints++;
            }
        }

        for (Point point : points) {
            ArrayList<Point> p = Polygon.computeVoronoiCell(point, points);
            drawPolygon(g2, p);
        }

        for (Point p : points) {
            drawPoint(g2, (int) p.x, (int) p.y);
        }

        g2.dispose();

        ImageExporter.exportToPNG(image);
    }
}
