package davidmarino.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * Class {@code Parameters} holds the values that influence the model.
 */

@Component
@Data
public class Parameters {
    @JsonProperty("mapScale")
    public int mapScale = 5;

    @JsonProperty("numberOfDungeons")
    public int numberOfDungeons = 5;

    @JsonProperty("numberOfRooms")
    public int numberOfRooms = 10;

    @JsonProperty("distanceBetweenRooms")
    public int distanceBetweenRooms = 10;

    @JsonProperty("minimumRoomWidth")
    public int minimumRoomWidth = 20;

    @JsonProperty("maximumRoomWidth")
    public int maximumRoomWidth = 50;

    @JsonProperty("minimumRoomHeight")
    public int minimumRoomHeight = 20;

    @JsonProperty("maximumRoomHeight")
    public int maximumRoomHeight = 50;

    @JsonProperty("vertexSize")
    public int vertexSize = 5;

    @JsonProperty("edgeSize")
    public int edgeSize = 1;

    @JsonProperty("numberOfLloydIterations")
    public int numberOfLloydIterations = 5;

    @JsonProperty("waterLevel")
    public double waterLevel = 0.4;

    @JsonProperty("coastLevel")
    public double coastLevel = 0.45;

    @JsonProperty("whiteCapLevel")
    public double whiteCapLevel = 0.85;

    @JsonProperty("imageFileName")
    public String imageFileName = "output/map.png";

    @JsonProperty("backgroundColor")
    public int[] backgroundColor = {255, 255, 255};

    @JsonProperty("polygonVertexColor")
    public int[] polygonVertexColor = {0, 0, 255};

    @JsonProperty("polygonSiteColor")
    public int[] polygonSiteColor = {255, 0, 0};

    @JsonProperty("polygonBorderColor")
    public int[] polygonBorderColor = {0, 0, 255};

    @JsonProperty("startPercent")
    public double startPercent = 0.5;

    @JsonProperty("spreadChance")
    public double spreadChance = 0.8;

    @JsonProperty("maxChunks")
    public int maxChunks = 20;

    @JsonProperty("maxStepsPerChunk")
    public int maxStepsPerChunk = 20;

    @JsonProperty("monsterScrapeUrl")
    public String monsterScrapeUrl;

    public Parameters(int mapScale,
                      int numberOfDungeons,
                      int numberOfRooms,
                      int distanceBetweenRooms,
                      int minimumRoomWidth,
                      int maximumRoomWidth,
                      int minimumRoomHeight,
                      int maximumRoomHeight,
                      int vertexSize,
                      int edgeSize,
                      int numberOfLloydIterations,
                      double waterLevel,
                      double coastLevel,
                      double whiteCapLevel,
                      int[] backgroundColor,
                      int[] polygonVertexColor,
                      int[] polygonSiteColor,
                      int[] polygonBorderColor,
                      double startPercent,
                      double spreadChance,
                      int maxChunks,
                      int maxStepsPerChunk,
                      String monsterScrapeUrl) {
        this.mapScale = mapScale;
        this.numberOfDungeons = numberOfDungeons;
        this.numberOfRooms = numberOfRooms;
        this.distanceBetweenRooms = distanceBetweenRooms;
        this.minimumRoomWidth = minimumRoomWidth;
        this.maximumRoomWidth = maximumRoomWidth;
        this.minimumRoomHeight = minimumRoomHeight;
        this.maximumRoomHeight = maximumRoomHeight;
        this.vertexSize = vertexSize;
        this.edgeSize = edgeSize;
        this.numberOfLloydIterations = numberOfLloydIterations;
        this.waterLevel = waterLevel;
        this.coastLevel = coastLevel;
        this.whiteCapLevel = whiteCapLevel;
        this.backgroundColor = backgroundColor;
        this.polygonVertexColor = polygonVertexColor;
        this.polygonSiteColor = polygonSiteColor;
        this.polygonBorderColor = polygonBorderColor;
        this.startPercent = startPercent;
        this.spreadChance = spreadChance;
        this.maxChunks = maxChunks;
        this.maxStepsPerChunk = maxStepsPerChunk;
        this.monsterScrapeUrl = monsterScrapeUrl;
    }
    public Parameters() {

    }
}
