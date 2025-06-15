package davidmarino;

import java.awt.*;

/**
 * Class {@code Parameters} holds the values that influence the model.
 */
public class Parameters {
    public static final int height = 5000;
    public static final int width = 5000;
    public static final int numberOfPoints = 1000;

    public static final int vertexSize = 50;
    public static final int edgeSize = 25;

    public static final int maxLloydIterations = 5;

    public static final double waterLevel = 0.5;
    public static final double whiteCapLevel = 0.85;

    public static final String imageFileName = "output/noisyedges.png";

    public static final Color backgroundColor = new Color(255, 255, 228);
    public static final Color polygonVertexColor = Color.BLUE;
    public static final Color polygonSiteColor = Color.RED;
    public static final Color polygonBorderColor = Color.BLACK;

    public static final double startPercent = 0.5; // percent of coast tiles to use as starting points
    public static final double spreadChance = 0.8; // chance of spreading to a neighbor
    public static final int maxChunks = 10;       // max number of separate erosion chunks
    public static final int maxStepsPerChunk = 20; // max spread steps per chunk
}
