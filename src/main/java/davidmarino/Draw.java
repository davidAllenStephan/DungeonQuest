package davidmarino;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

//        g2.setColor(Color.WHITE);
//        g2.setStroke(new BasicStroke(Parameters.edgeSize));
//        g2.drawPolygon(xPoints, yPoints, n);

        g2.setColor(color);
        g2.fillPolygon(xPoints, yPoints, n);
    }

    public void setBackground(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, Parameters.width, Parameters.height);
    }

    /**
     * Generates semi random points which are approximated using Voronoi Cells and Lloyd Relaxation algorithm.
     * @return {@code ArrayList<Point>}
     */
    public ArrayList<Point> generatePoints() {
        ArrayList<Point> sitePoints = Point.generateRandomPoints(Parameters.numberOfPoints);
        for (int i = 0; i < Parameters.maxLloydIterations; i++) {
            ArrayList<Point> adjustedSitePoints = new ArrayList<>();
            for (Point point : sitePoints) {
                Polygon p = Polygon.computeVoronoiCell(point, sitePoints);
                adjustedSitePoints.add(LloydRelaxation.findCentroid(p, point));
            }
            sitePoints = adjustedSitePoints;
        }
        return sitePoints;
    }

    /**
     * Generates voronoi polygons given the site points.
     * @param sitePoints to determine Voronoi polygon
     * @return {@code ArrayList<Polygon>}
     */
    public ArrayList<Polygon> generateVoronoiPolygons(ArrayList<Point> sitePoints) {
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (Point point : sitePoints) {
            Polygon poly = Polygon.computeVoronoiCell(point, sitePoints);
            polygons.add(poly);
        }
        return polygons;
    }

    public void findNeighbors(ArrayList<Polygon> polygons) {
        HashMap<Point, ArrayList<Polygon>> vertexToPolygonsMap = new HashMap<>();

        for (Polygon polygon : polygons) {
            for (Point vertex : polygon.vertices) {
                vertexToPolygonsMap
                        .computeIfAbsent(vertex, k -> new ArrayList<>())
                        .add(polygon);
            }
        }

        for (Polygon polygon : polygons) {
            for (Point vertex : polygon.vertices) {
                ArrayList<Polygon> adjacentPolygons = vertexToPolygonsMap.get(vertex);
                if (adjacentPolygons != null) {
                    for (Polygon neighbor : adjacentPolygons) {
                        if (neighbor != polygon) {
                            polygon.neighbors.add(neighbor);
                        }
                    }
                }
            }
        }
    }


    /**
     * Applies the z value of every polygons site which is used to determine elevation.
     * @param polygons to apply to
     */
    public void applyRadial(ArrayList<Polygon> polygons) {
        Radial radial = new Radial();
        for (Polygon poly : polygons) {
            radial.adjustToRadial(poly.site);
        }
    }

    /**
     * Renders List of polygons using g2 library.
     * @param g2 is the render library
     * @param polygons to be rendered
     */
    public void drawPolygons(Graphics2D g2, ArrayList<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            if (polygon.site.z >= Parameters.whiteCapLevel) {
                drawPolygon(g2, polygon, new Color((int) (polygon.site.z * 255), (int) (polygon.site.z * 255), (int) (polygon.site.z * 255)));
            } else if (polygon.site.z >= Parameters.waterLevel) {
                drawPolygon(g2, polygon, new Color(0, (int) (polygon.site.z * 255), 0));
            } else { // seaLevel < waterLevel < whiteCapLevel
                drawPolygon(g2, polygon, new Color(0, 0, (int) (polygon.site.z * 255)));
            }
        }
    }

    /**
     * Executes the start of the generation.
     */
    public void run() {
        BufferedImage image = new BufferedImage(Parameters.width, Parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        setBackground(g2);

        ArrayList<Point> sitePoints = generatePoints();

        ArrayList<Polygon> voronoiPolygons = generateVoronoiPolygons(sitePoints);

        findNeighbors(voronoiPolygons);
        applyRadial(voronoiPolygons);


        CoastMask cm = new CoastMask();
        cm.applyCoastMask(voronoiPolygons);

        drawPolygons(g2, voronoiPolygons);




        g2.dispose();
        ImageExporter.exportToPNG(image);
    }
}
