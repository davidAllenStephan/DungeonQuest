package davidmarino;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    private void drawPoint(Graphics2D g2, int x, int y, Color color) {
        g2.setColor(color);
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

        g2.setColor(color);
        g2.fillPolygon(xPoints, yPoints, n);
    }

    private void drawPolygonBorder(Graphics2D g2, Polygon polygon, Color color) {
        int n = polygon.vertices.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        for (int i = 0; i < n; i++) {
            xPoints[i] = (int) Math.round(polygon.vertices.get(i).x);
            yPoints[i] = (int) Math.round(polygon.vertices.get(i).y);
        }

        g2.setColor(color);
        g2.setStroke(new BasicStroke(Parameters.edgeSize));
        g2.drawPolygon(xPoints, yPoints, n);

    }

    public void setBackground(Graphics2D g2, Color color) {
        g2.setColor(color);
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
        HashMap<Point, HashSet<Polygon>> vertexToPolygonsMap = new HashMap<>();
        for (Polygon polygon : polygons) {
            for (Point vertex : polygon.vertices) {
                vertexToPolygonsMap
                        .computeIfAbsent(vertex, k -> new HashSet<>())
                        .add(polygon);
            }
        }

        for (Polygon polygon : polygons) {
            for (Point vertex : polygon.vertices) {
                HashSet<Polygon> adjacentPolygons = vertexToPolygonsMap.get(vertex);
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

    public void drawPolygonBorders(Graphics2D g2, ArrayList<Polygon> polygons, Color color) {
        for (Polygon polygon : polygons) {
            drawPolygonBorder(g2, polygon, color);
        }
    }

    public void drawPoints(Graphics2D g2, ArrayList<Point> points, Color color) {
        for (Point point : points) {
            drawPoint(g2, (int) point.x, (int) point.y, color);
        }
    }

    public void drawLine(Graphics2D g2, Line line, Color color) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(Parameters.edgeSize));
        g2.drawLine((int) line.A.x, (int) line.A.y, (int) line.B.x, (int) line.B.y);
    }

    /**
     * Executes the start of the generation.
     */
    public void run() {
        BufferedImage image = new BufferedImage(Parameters.width, Parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        setBackground(g2, Parameters.backgroundColor);

        ArrayList<Point> sitePoints = generatePoints();
        ArrayList<Polygon> voronoiPolygons = generateVoronoiPolygons(sitePoints);
        findNeighbors(voronoiPolygons);

        applyRadial(voronoiPolygons);

        CoastMask cm = new CoastMask();
        ArrayList<Polygon> coastMask = cm.getCoastMask(voronoiPolygons);
        cm.erodeInwardFromRandomCoastPoints(coastMask, Parameters.startPercent, Parameters.spreadChance, Parameters.maxChunks, Parameters.maxStepsPerChunk);
//
//        drawPolygons(g2, voronoiPolygons);

        /**
         * We need to find the quadrilaterals of each polygon.
         * The quadrilaterals must be made of the polygon and its neighbor which we have.
         * If a polygon has two matching vertices with a neighbor then that is the connection of the quadrilateral.
         */

//        for (Polygon polygon : voronoiPolygons) {
//            polygon.applyNoisyBoarder();
//        }


//        ArrayList<Point> polygonPoints = new ArrayList<>();
//        for (Polygon polygon : voronoiPolygons) {
//            polygonPoints.addAll(polygon.vertices);
//        }



//        ArrayList<Polygon> quadrilaterals = new ArrayList<>(); // initial quads
//        for (Polygon polygon : voronoiPolygons) {
//            quadrilaterals.addAll(polygon.findQuadrilaterals());
//        }

//        for (Polygon polygon : quadrilaterals) {
//            ArrayList<Polygon> subdivided = polygon.subdivideByDiversion(0.6);
//            Line l1 = new Line(subdivided.getFirst().vertices.get(2), polygon.vertices.get(1));
//            Line l2 = new Line(subdivided.getFirst().vertices.get(2), polygon.vertices.get(3));
//            drawLine(g2, l1, Color.BLUE);
//            drawLine(g2, l2, Color.BLUE);
//            drawPolygonBorder(g2, polygon, Color.GREEN);
//            drawPolygonBorders(g2, subdivided, Color.RED);
//            ArrayList<Polygon> inverse = Polygon.inverseQuadrilateral(polygon, subdivided);
//            drawPolygonBorder(g2, inverse.getFirst(), new Color(255,0,255));
//            drawPolygonBorder(g2, inverse.get(1), new Color(255,0,255));
//        }

        for (Polygon polygon : voronoiPolygons) {
            polygon.applyNoisyBorder(0.7);
        }

        drawPolygons(g2, voronoiPolygons);



//        Polygon polygon = quadrilaterals.get(0);
//        ArrayList<Polygon> subdivided = polygon.subdivideByDiversion(0.6);
//        Line l1 = new Line(subdivided.getFirst().vertices.get(2), polygon.vertices.get(1));
//        Line l2 = new Line(subdivided.getFirst().vertices.get(2), polygon.vertices.get(3));
//        drawLine(g2, l1, Color.BLUE);
//        drawLine(g2, l2, Color.BLUE);
//        drawPolygonBorder(g2, polygon, Color.GREEN);
//        drawPolygonBorders(g2, subdivided, Color.RED);
//        ArrayList<Polygon> inverse = Polygon.inverseQuadrilateral(polygon, subdivided);
//        drawPolygonBorder(g2, inverse.getFirst(), new Color(255,0,255));
//        drawPolygonBorder(g2, inverse.get(1), new Color(255,0,255));




//        ArrayList<Polygon> quad2 = inverse.getFirst().findQuadrilaterals();
//        ArrayList<Polygon> subdivided2 = quad2.getFirst().subdivideByDiversion(0.7);
//
//        drawPolygonBorders(g2, subdivided2, Color.RED);
//        Line l3 = new Line(subdivided2.getFirst().vertices.get(2), quadrilaterals.getFirst().vertices.get(1));
//        Line l4 = new Line(subdivided2.getFirst().vertices.get(2), quadrilaterals.getFirst().vertices.get(3));
//
//        drawLine(g2, l3, Color.BLUE);
//        drawLine(g2, l4, Color.BLUE);




//        Line l = new Line(quadrilaterals.getFirst().vertices.getFirst(), quadrilaterals.getFirst().vertices.get(2));
//        drawLine(g2, l, Color.RED);



//        drawPoints(g2, polygonPoints, Parameters.polygonVertexColor);
//        drawPoints(g2, sitePoints, Parameters.polygonSiteColor);

        g2.dispose();
        ImageExporter.exportToPNG(image);
    }
}
