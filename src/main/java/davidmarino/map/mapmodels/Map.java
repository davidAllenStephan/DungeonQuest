package davidmarino.map.mapmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import davidmarino.model.Parameters;
import davidmarino.map.mapservice.MapService;
import davidmarino.map.mapservice.SiteCollectionService;
import davidmarino.map.mapviews.MapView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Component
public class Map {
    @JsonProperty("dungeon_sites")
    public SiteCollection dungeonSites;
    @JsonProperty("map_image")
    public String mapImage;

    @Autowired
    private transient MapService mapService;
    @Autowired
    private transient SiteCollectionService siteCollectionService;
    @Autowired
    private transient MapView mapView;

    private transient int width;
    private transient int height;
    private transient int numberOfPoints;

    public Map() {

    }

    public Map(Parameters parameters) {
        mapService = new MapService();
        siteCollectionService = new SiteCollectionService();
        mapView = new MapView();

        width = parameters.mapScale * 1000;
        height = parameters.mapScale * 1000;
        numberOfPoints = parameters.mapScale * 200;

        ArrayList<Polygon> polygons = mapService.getMapPolygons(numberOfPoints, parameters.numberOfLloydIterations, width, height, parameters.waterLevel, parameters.startPercent, parameters.spreadChance, parameters.maxChunks, parameters.maxStepsPerChunk, parameters.coastLevel);
        BufferedImage mapBufferedImage = mapView.getGraphics2D(polygons, width, height, parameters.backgroundColor, parameters.waterLevel, parameters.coastLevel, parameters.whiteCapLevel);
        mapImage = mapView.getBase64(mapBufferedImage);
        dungeonSites = siteCollectionService.getSites(polygons, parameters.waterLevel, parameters.numberOfDungeons, width, height);
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
