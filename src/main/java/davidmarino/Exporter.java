package davidmarino;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class {@code ImageExporter} exports {@code BufferedImage} to media.
 * @author David Marino
 * @version 13 Jun 2025
 */
public class Exporter {

    private static final Logger logger = LoggerFactory.getLogger(Exporter.class);

    /**
     * Exports image to png file.
     * @param image to be exported
     * @param targetWidth of the exported image
     * @param targetHeight of the exported image
     */
    public static void exportToPNG(BufferedImage image, String imageFileName, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        try {
            ImageIO.write(resizedImage, "png", new File(imageFileName));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void exportToJson(Object object, String fileName) {
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
