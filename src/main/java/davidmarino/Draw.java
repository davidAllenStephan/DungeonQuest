package davidmarino;

import davidmarino.controller.LineController;
import davidmarino.controller.PointController;
import davidmarino.controller.PolygonController;
import davidmarino.model.Point;
import davidmarino.model.Polygon;
import davidmarino.view.PolygonView;

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
     * Renders the background.
     * @param g2 is the render library
     * @param color of the background
     */
    public void setBackground(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.fillRect(0, 0, Parameters.width, Parameters.height);
    }

    /**
     * Executes the start of the generation.
     */
    public void run() {
        BufferedImage image = new BufferedImage(Parameters.width, Parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        setBackground(g2, Parameters.backgroundColor);

        PointController pc = new PointController();
        PolygonController pcp = new PolygonController();


        ArrayList<Point> sitePoints = pc.generateRandomPoints(Parameters.numberOfPoints);
        sitePoints = LloydRelaxation.applyLloydRelaxation(sitePoints);

        ArrayList<Polygon> voronoiPolygons = pcp.generateVoronoiPolygons(sitePoints);

        pcp.findNeighbors(voronoiPolygons);

        for (Polygon polygon : voronoiPolygons) {
            pcp.applyNoisyBorder(polygon, 0.7);
        }

        RadialHeightMap.applyRadialHeightMap(voronoiPolygons);

        CoastMask cm = new CoastMask();
        ArrayList<Polygon> coastMask = cm.getCoastMask(voronoiPolygons);
        cm.erodeInwardFromRandomCoastPoints(coastMask, Parameters.startPercent, Parameters.spreadChance, Parameters.maxChunks, Parameters.maxStepsPerChunk);

        ArrayList<Polygon> coast = cm.getCoastMask(voronoiPolygons);
        Random r = new Random();
        for (Polygon polygon : coast) {
            polygon.site.z = r.nextDouble(Parameters.waterLevel, Parameters.coastLevel);
        }

        ArrayList<Point> polygonPoints = new ArrayList<>();
        for (Polygon p : voronoiPolygons) {
            polygonPoints.addAll(p.vertices);
        }

        PolygonView.drawFilledPolygons(g2, voronoiPolygons);
        g2.dispose();
        ImageExporter.exportToPNG(image, 500, 500);
    }
}
