package davidmarino.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import davidmarino.service.dungeonservice.DungeonService;
import davidmarino.model.Parameters;
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
public class DungeonQuestService {
    protected final Parameters parameters;
    @Autowired
    protected static final Logger logger = LoggerFactory.getLogger(DungeonService.class);
    @Autowired
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    public DungeonQuestService(Parameters parameters) {
        this.parameters = parameters;
    }

    public void setBackground(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.fillRect(0, 0, parameters.width, parameters.height);
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

}
