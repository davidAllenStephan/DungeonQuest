package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.RoomType;
import davidmarino.dungeon.dungeonmodels.TileType;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public final class TileMap {

    private final Map<RoomType, BufferedImage> tiles = new HashMap<>();

    public BufferedImage getTile(TileType tileType) {
        return tiles.get(tileType);
    }

    public TileMap() {
//        try {
//            tiles.put(RoomType.ZONE, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_Camping_16x16_Rock_1.png"))));
//            tiles.put(RoomType.CORRIDOR, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_Camping_16x16_Rock_1.png"))));
//            tiles.put(RoomType.SITE, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_Camping_16x16_Rock_1.png"))));
//            tiles.put(RoomType.NULL, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_City_Terrains_16x16_Sidewalk_4_10.png"))));
//            tiles.put(RoomType.NULL, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/ME_Singles_City_Terrains_16x16_Sidewalk_4_10.png"))));
//
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to load tile images", e);
//        }
    }
}
