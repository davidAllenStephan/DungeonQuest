package davidmarino.model;

import davidmarino.model.mapmodels.Polygon;
import davidmarino.service.DungeonQuestService;
import davidmarino.service.mapservice.*;
import davidmarino.view.mapviews.PolygonView;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

@Service
public class Map extends DungeonQuestService {

    public Map(Parameters parameters) {
        super(parameters);
    }

    public byte[] runMap() {
        BufferedImage map = new BufferedImage(parameters.width, parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = map.createGraphics();

        setBackground(g2, new Color(parameters.backgroundColor[0], parameters.backgroundColor[1], parameters.backgroundColor[2]));

        PointService pc = new PointService();
        PolygonService pcp = new PolygonService();


        ArrayList<davidmarino.model.mapmodels.Point> sitePoints = pc.generateRandomPoints(parameters.numberOfPoints, parameters.width, parameters.height);
        sitePoints = LloydRelaxation.applyLloydRelaxation(sitePoints, parameters.numberOfLloydIterations, parameters.width, parameters.height);

        ArrayList<davidmarino.model.mapmodels.Polygon> voronoiPolygons = pcp.generateVoronoiPolygons(sitePoints, parameters.width, parameters.height);

        pcp.findNeighbors(voronoiPolygons);

        for (davidmarino.model.mapmodels.Polygon polygon : voronoiPolygons) {
            pcp.applyNoisyBorder(polygon, 0.7);
        }

        RadialHeightMap.applyRadialHeightMap(voronoiPolygons, parameters.width, parameters.height);

        CoastMask cm = new CoastMask();
        ArrayList<davidmarino.model.mapmodels.Polygon> coastMask = cm.getCoastMask(voronoiPolygons, parameters.waterLevel);
        cm.erodeInwardFromRandomCoastPoints(coastMask, parameters.startPercent, parameters.spreadChance, parameters.maxChunks, parameters.maxStepsPerChunk, parameters.waterLevel);

        ArrayList<davidmarino.model.mapmodels.Polygon> coast = cm.getCoastMask(voronoiPolygons, parameters.waterLevel);
        Random r = new Random();
        for (davidmarino.model.mapmodels.Polygon polygon : coast) {
            polygon.site.z = r.nextDouble(parameters.waterLevel, parameters.coastLevel);
        }

        ArrayList<davidmarino.model.mapmodels.Point> polygonPoints = new ArrayList<>();
        for (Polygon p : voronoiPolygons) {
            polygonPoints.addAll(p.vertices);
        }

        PolygonView.drawFilledPolygons(g2, voronoiPolygons, parameters.waterLevel, parameters.coastLevel, parameters.whiteCapLevel);
        g2.dispose();

        return renderToPng(map);
    }
}
