package davidmarino.dungeon.dungeonviews;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TileMap {

    private final Map<String, BufferedImage> tiles = new HashMap<>();
    public BufferedImage getTile(String key) {
        return tiles.get(key);
    }
    public TileMap() {
        try {
            tiles.put("topLeft", ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/23_MIlitary_Base_16x16_External_Wall_Internal_Corner_Top_Left.png"))));
            tiles.put("topRight", ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/23_MIlitary_Base_16x16_External_Wall_Internal_Corner_Top_Right.png"))));
            tiles.put("bottomLeft", ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/23_MIlitary_Base_16x16_External_Wall_Internal_Corner_Bottom_Left.png"))));
            tiles.put("bottomRight", ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/23_MIlitary_Base_16x16_External_Wall_Internal_Corner_Bottom_Right.png"))));
            tiles.put("wall", ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/23_MIlitary_Base_16x16_External_Wall_Middle.png"))));
            tiles.put("floor", ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_City_Terrains_16x16_Sidewalk_4_10.png"))));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tile images", e);
        }
    }
}
