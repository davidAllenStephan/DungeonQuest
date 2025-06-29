package davidmarino.service.mapservice;

import davidmarino.model.Parameters;
import davidmarino.model.mapmodels.Point;
import davidmarino.model.mapmodels.Polygon;
import davidmarino.service.DungeonQuestService;
import davidmarino.view.mapviews.PolygonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

@Service
public class MapService extends DungeonQuestService {
    @Autowired
    private PointService pointService;
    @Autowired
    private PolygonService polygonService;

    @Autowired
    private final static Logger logger = LoggerFactory.getLogger(MapService.class);

    public MapService(Parameters parameters) {
        super(parameters);
        pointService = new PointService();
        polygonService = new PolygonService();
    }

    public String getBase64() {
        int scale = parameters.mapScale;

        if (scale <= 0) {
            logger.error("Invalid map scale");
            throw new IllegalArgumentException("Invalid map scale: must be > 0. Found: " + scale);
        }

        int width = scale * 1000;
        int height = scale * 1000;
        int numberOfPoints = scale * 250;

        BufferedImage map = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = map.createGraphics();
        setBackground(g2, new Color(parameters.backgroundColor[0], parameters.backgroundColor[1], parameters.backgroundColor[2]));
        ArrayList<Point> sitePoints = pointService.generateRandomPoints(numberOfPoints, width, height);
        sitePoints = LloydRelaxation.applyLloydRelaxation(sitePoints, parameters.numberOfLloydIterations, width, height);
        ArrayList<Polygon> voronoiPolygons = polygonService.generateVoronoiPolygons(sitePoints, width, height);
        polygonService.findNeighbors(voronoiPolygons);
        for (Polygon polygon : voronoiPolygons) {
            polygonService.applyNoisyBorder(polygon, 0.7);
        }
        RadialHeightMap.applyRadialHeightMap(voronoiPolygons, width, height);
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

        return getBase64(map);
    }
}