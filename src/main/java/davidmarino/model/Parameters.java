package davidmarino.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Class {@code Parameters} holds the values that influence the model.
 */
@Data
public class Parameters {
    @JsonProperty("height")
    public int height;
    @JsonProperty("width")
    public int width;
    @JsonProperty("numberOfPoints")
    public int numberOfPoints;
    @JsonProperty("numberOfRooms")
    public int numberOfRooms;
    @JsonProperty("distanceBetweenRooms")
    public int distanceBetweenRooms;
    @JsonProperty("minimumRoomWidth")
    public int minimumRoomWidth;
    @JsonProperty("maximumRoomWidth")
    public int maximumRoomWidth;
    @JsonProperty("minimumRoomHeight")
    public int minimumRoomHeight;
    @JsonProperty("maximumRoomHeight")
    public int maximumRoomHeight;

    @JsonProperty("vertexSize")
    public int vertexSize;
    @JsonProperty("edgeSize")
    public int edgeSize;

    @JsonProperty("numberOfLloydIterations")
    public int numberOfLloydIterations;

    @JsonProperty("waterLevel")
    public double waterLevel;
    @JsonProperty("coastLevel")
    public double coastLevel;
    @JsonProperty("whiteCapLevel")
    public double whiteCapLevel;

    @JsonProperty("imageFileName")
    public String imageFileName;

    @JsonProperty("backgroundColor")
    public int[] backgroundColor;
    @JsonProperty("polygonVertexColor")
    public int[] polygonVertexColor;
    @JsonProperty("polygonSiteColor")
    public int[] polygonSiteColor;
    @JsonProperty("polygonBorderColor")
    public int[] polygonBorderColor;

    @JsonProperty("startPercent")
    public double startPercent;
    @JsonProperty("spreadChance")
    public double spreadChance;
    @JsonProperty("maxChunks")
    public int maxChunks;
    @JsonProperty("maxStepsPerChunk")
    public int maxStepsPerChunk;

    public Parameters(int height, int width, int numberOfPoints, int numberOfRooms, int distanceBetweenRooms, int minimumRoomWidth, int maximumRoomWidth, int minimumRoomHeight, int maximumRoomHeight, int vertexSize, int edgeSize, int numberOfLloydIterations, double waterLevel, double coastLevel, double whiteCapLevel, String imageFileName, int[] backgroundColor, int[] polygonVertexColor, int[] polygonSiteColor, int[] polygonBorderColor, double startPercent, double spreadChance, int maxChunks, int maxStepsPerChunk) {
        this.height = height;
        this.width = width;
        this.numberOfPoints = numberOfPoints;
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
        this.imageFileName = imageFileName;
        this.backgroundColor = backgroundColor;
        this.polygonVertexColor = polygonVertexColor;
        this.polygonSiteColor = polygonSiteColor;
        this.polygonBorderColor = polygonBorderColor;
        this.startPercent = startPercent;
        this.spreadChance = spreadChance;
        this.maxChunks = maxChunks;
        this.maxStepsPerChunk = maxStepsPerChunk;
    }
    public Parameters() {

    }
}
