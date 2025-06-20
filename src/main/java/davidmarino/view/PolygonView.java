package davidmarino.view;

import davidmarino.model.Polygon;

import java.awt.*;
import java.util.ArrayList;

public class PolygonView {

    /**
     * Renders a polygon.
     * @param g2 is the render library
     * @param polygon to be rendered
     * @param color of the filled polygon
     */
    public static void drawFilledPolygon(Graphics2D g2, davidmarino.model.Polygon polygon, Color color) {
        int n = polygon.vertices.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];
        for (int i = 0; i < n; i++) {
            xPoints[i] = (int) Math.round(polygon.vertices.get(i).x);
            yPoints[i] = (int) Math.round(polygon.vertices.get(i).y);
        }
        g2.setColor(color);
        g2.fillPolygon(xPoints, yPoints, n);
    }

    /**
     * Renders polygons border
     * @param g2 is the render library
     * @param polygon to be rendered
     * @param color of the polygon border
     */
    public static void drawPolygonBorder(Graphics2D g2, Polygon polygon, int edgeWeight, Color color) {
        int n = polygon.vertices.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];
        for (int i = 0; i < n; i++) {
            xPoints[i] = (int) Math.round(polygon.vertices.get(i).x);
            yPoints[i] = (int) Math.round(polygon.vertices.get(i).y);
        }
        g2.setColor(color);
        g2.setStroke(new BasicStroke(edgeWeight));
        g2.drawPolygon(xPoints, yPoints, n);

    }

    /**
     * Renders set of filled polygons.
     * Colors based on z value of Polygon.
     * @param g2 is the render library
     * @param polygons to be rendered
     */
    public static void drawFilledPolygons(Graphics2D g2, ArrayList<davidmarino.model.Polygon> polygons, double waterLevel, double coastLevel, double whiteCapLevel) {
        for (davidmarino.model.Polygon polygon : polygons) {
            if (polygon.site.z < waterLevel) {
                drawFilledPolygon(g2, polygon, new Color(0, 0, (int) (polygon.site.z * 255)));
            } else if (polygon.site.z < coastLevel) {
                drawFilledPolygon(g2, polygon, new Color((int) (polygon.site.z * 255) * 2, (int) (polygon.site.z * 255) * 2, 0));
            } else if (polygon.site.z < whiteCapLevel) {
                drawFilledPolygon(g2, polygon, new Color(0, (int) (polygon.site.z * 255), 0));
            } else {
                drawFilledPolygon(g2, polygon, new Color((int) (polygon.site.z * 255), (int) (polygon.site.z * 255), (int) (polygon.site.z * 255)));
            }
        }
    }

    /**
     * Renders set of polygon boarders.
     * @param g2 is the render library
     * @param polygons to be rendered
     * @param color of the polygon boarder
     */
    public static void drawPolygonBorders(Graphics2D g2, ArrayList<davidmarino.model.Polygon> polygons, int edgeWeight, Color color) {
        for (Polygon polygon : polygons) {
            drawPolygonBorder(g2, polygon, edgeWeight, color);
        }
    }
}
