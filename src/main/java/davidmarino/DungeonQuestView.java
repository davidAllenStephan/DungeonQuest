package davidmarino;

import davidmarino.dungeon.dungeonviews.DungeonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class DungeonQuestView {
    @Autowired
    protected static final Logger logger = LoggerFactory.getLogger(DungeonView.class);

    public DungeonQuestView() {

    }

    public byte[] getBytes(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Image rendering failed", e);
        }
    }

    public String getBase64(BufferedImage image) {
        byte[] bytes = getBytes(image);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public void setBackground(Graphics2D g2, Color color, int width, int height) {
        g2.setColor(color);
        g2.fillRect(0, 0, width, height);
    }

}