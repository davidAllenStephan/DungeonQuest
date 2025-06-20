package davidmarino;

import com.fasterxml.jackson.databind.ObjectMapper;
import davidmarino.model.*;
import davidmarino.model.Point;
import davidmarino.model.Polygon;
import davidmarino.service.*;
import davidmarino.view.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code Draw} class is used to set up and render all objects.
 * @version 13 Jun 2025
 * @author David Marino
 */
public class Draw {

    public Parameters parameters;

    public Draw(Parameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Renders the background.
     * @param g2 is the render library
     * @param color of the background
     */
    public void setBackground(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.fillRect(0, 0, parameters.width, parameters.height);
    }

    /**
     * Executes the start of the generation.
     */
    public byte[] runDungeon() {
        BufferedImage dungeon = new BufferedImage(parameters.width, parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2Dungeon = dungeon.createGraphics();

        setBackground(g2Dungeon, new Color(parameters.backgroundColor[0], parameters.backgroundColor[1], parameters.backgroundColor[2]));

        ArrayList<Zone> zones = ZoneService.generateZones(parameters.numberOfRooms, parameters.width, parameters.height, parameters.minimumRoomWidth, parameters.minimumRoomHeight, parameters.maximumRoomWidth, parameters.maximumRoomHeight);
        ZoneView.drawZones(g2Dungeon, zones, parameters.edgeSize);

        g2Dungeon.dispose();
        ByteArrayOutputStream baosDungeon = new ByteArrayOutputStream();
        try {
            ImageIO.write(dungeon, "png", baosDungeon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] dungeonBytes = baosDungeon.toByteArray();

        return dungeonBytes;
    }

    public byte[] runMap() {
        BufferedImage map = new BufferedImage(parameters.width, parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = map.createGraphics();

        setBackground(g2, new Color(parameters.backgroundColor[0], parameters.backgroundColor[1], parameters.backgroundColor[2]));

        PointService pc = new PointService();
        PolygonService pcp = new PolygonService();


        ArrayList<Point> sitePoints = pc.generateRandomPoints(parameters.numberOfPoints, parameters.width, parameters.height);
        sitePoints = LloydRelaxation.applyLloydRelaxation(sitePoints, parameters.numberOfLloydIterations, parameters.width, parameters.height);

        ArrayList<Polygon> voronoiPolygons = pcp.generateVoronoiPolygons(sitePoints, parameters.width, parameters.height);

        pcp.findNeighbors(voronoiPolygons);

        for (Polygon polygon : voronoiPolygons) {
            pcp.applyNoisyBorder(polygon, 0.7);
        }

        RadialHeightMap.applyRadialHeightMap(voronoiPolygons, parameters.width, parameters.height);

        CoastMask cm = new CoastMask();
        ArrayList<Polygon> coastMask = cm.getCoastMask(voronoiPolygons, parameters.waterLevel);
        cm.erodeInwardFromRandomCoastPoints(coastMask, parameters.startPercent, parameters.spreadChance, parameters.maxChunks, parameters.maxStepsPerChunk, parameters.waterLevel);

        ArrayList<Polygon> coast = cm.getCoastMask(voronoiPolygons, parameters.waterLevel);
        Random r = new Random();
        for (Polygon polygon : coast) {
            polygon.site.z = r.nextDouble(parameters.waterLevel, parameters.coastLevel);
        }

        ArrayList<Point> polygonPoints = new ArrayList<>();
        for (Polygon p : voronoiPolygons) {
            polygonPoints.addAll(p.vertices);
        }

        PolygonView.drawFilledPolygons(g2, voronoiPolygons, parameters.waterLevel, parameters.coastLevel, parameters.whiteCapLevel);
        g2.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(map, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] mapBytes = baos.toByteArray();
        return mapBytes;
    }
}
