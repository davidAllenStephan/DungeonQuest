package davidmarino.map.mapservice;

import davidmarino.map.mapmodels.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MapService {
    @Autowired
    private PointService pointService;
    @Autowired
    private PolygonService polygonService;
    @Autowired
    private CoastMaskService coastMaskService;

    @Autowired
    private final static Logger logger = LoggerFactory.getLogger(MapService.class);

    public MapService() {
        pointService = new PointService();
        polygonService = new PolygonService();
        coastMaskService = new CoastMaskService();
    }

    public ArrayList<Polygon> getMapPolygons(int numberOfPoints, int numberOfLloydIterations, int width, int height, double waterLevel, double startPercent, double spreadChance, int maxChunks, int maxStepsPerChunk, double coastLevel) {
        ArrayList<Polygon> voronoiPolygons = polygonService.createVoronoiPolygons(numberOfPoints, numberOfLloydIterations, width, height);
        polygonService.findNeighbors(voronoiPolygons);
        for (Polygon polygon : voronoiPolygons) {
            polygonService.applyNoisyBorder(polygon, 0.7);
        }
        RadialHeightMap.applyRadialHeightMap(voronoiPolygons, width, height);
        coastMaskService.degenerateCoast(voronoiPolygons, waterLevel, startPercent, spreadChance, maxChunks, maxStepsPerChunk, coastLevel);
        return voronoiPolygons;
    }
}