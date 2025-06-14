package davidmarino;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class {@code ImageExporter} exports {@code BufferedImage} to media.
 * @author David Marino
 * @version 13 Jun 2025
 */
public class ImageExporter {
    public static void exportToPNG(BufferedImage image) {
        try {
            ImageIO.write(image, "png", new File(Parameters.imageFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Image saved to drawing.png");
    }
}
