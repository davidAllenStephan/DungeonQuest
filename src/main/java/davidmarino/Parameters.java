package davidmarino;

import java.awt.*;

/**
 * Class {@code Parameters} holds the values that influence the model.
 */
public class Parameters {
    public static final int height = 400;
    public static final int width = 400;
    public static final int numberOfPoints = 1000;

    public static final int vertexSize = 5;
    public static final int edgeSize = 1;

    public static final int maxLloydIterations = 5;

    public static final double waterLevel = 0.4;
    public static final double coastLevel = waterLevel + 0.05;
    public static final double whiteCapLevel = 0.85;

    public static final String imageFileName = "output/map.png";

    public static final Color backgroundColor = Color.WHITE;
    public static final Color polygonVertexColor = Color.BLUE;
    public static final Color polygonSiteColor = Color.RED;
    public static final Color polygonBorderColor = Color.BLACK;

    public static final double startPercent = 0.5; // percent of coast tiles to use as starting points
    public static final double spreadChance = 0.8; // chance of spreading to a neighbor
    public static final int maxChunks = 20;        // max number of separate erosion chunks
    public static final int maxStepsPerChunk = 20; // max spread steps per chunk
}
