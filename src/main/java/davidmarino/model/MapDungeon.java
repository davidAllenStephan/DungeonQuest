package davidmarino.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import davidmarino.model.mapmodels.Point;
import davidmarino.model.mapmodels.Polygon;
import davidmarino.model.dungeonmodels.Zone;
import davidmarino.service.dungeonservice.ZoneService;
import davidmarino.service.mapservice.*;
import davidmarino.view.dungeonviews.ZoneView;
import davidmarino.view.mapviews.PolygonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

/**
 *
 * The {@code Draw} class is used to set up and render all objects.
 * @version 13 Jun 2025
 * @author David Marino
 */
@Service
public class MapDungeon {

    private final Parameters parameters;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(MapDungeon.class);

    @Autowired
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public MapDungeon(Parameters parameters) {
        this.parameters = parameters;
    }

    // Fix immediately
    public static String getBase64(String requestBody, boolean isDungeon) {
        Parameters parameters = null;
        try {
            parameters = objectMapper.readValue(requestBody, Parameters.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        MapDungeon mapDungeon = new MapDungeon(parameters);
        byte[] imageBytes = isDungeon ? mapDungeon.runDungeon() : mapDungeon.runMap();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private byte[] renderToPng(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Image rendering failed", e);
        }
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
        return renderToPng(dungeon);
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

        return renderToPng(map);
    }
}
