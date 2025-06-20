package davidmarino.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class {@code ImageExporter} exports {@code BufferedImage} to media.
 * @author David Marino
 * @version 13 Jun 2025
 */
public class ImageExporter {

    /**
     * Exports image to png file.
     * @param image to be exported
     * @param targetWidth of the exported image
     * @param targetHeight of the exported image
     */
    public static void exportToPNG(BufferedImage image, String imageFileName, int targetWidth, int targetHeight) {
        try {
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(image, 0, 0, targetWidth, targetHeight, null);
            g2d.dispose();
            ImageIO.write(resizedImage, "png", new File(imageFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
