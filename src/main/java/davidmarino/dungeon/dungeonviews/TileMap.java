package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.ZoneType;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public final class TileMap {

    private final Map<ZoneType, BufferedImage> tiles = new HashMap<>();

    public BufferedImage getTile(ZoneType zoneType) {
        return tiles.get(zoneType);
    }

    public TileMap() {
        try {
            tiles.put(ZoneType.ZONE, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_Camping_16x16_Rock_1.png"))));
            tiles.put(ZoneType.CORRIDOR, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_Camping_16x16_Rock_1.png"))));
            tiles.put(ZoneType.SITE, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_Camping_16x16_Rock_1.png"))));
            tiles.put(ZoneType.NULL, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_City_Terrains_16x16_Sidewalk_4_10.png"))));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tile images", e);
        }
    }
}
