package davidmarino.dungeon.dungeonmodels;

import davidmarino.dungeon.dungeonviews.TileMap;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class Tile {
    public int x;
    public int y;
    public ZoneType zoneType;

    private static final TileMap tileMap = new TileMap(); // Avoid @Autowired for manual creation

    public Tile() {
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.zoneType = ZoneType.NULL; // Default type
    }

    public Tile(int x, int y, ZoneType zoneType) {
        this.x = x;
        this.y = y;
        this.zoneType = zoneType;
    }

    public static Tile empty(int x, int y) {
        return new Tile(x, y, ZoneType.NULL);
    }

    public BufferedImage getTileAsset() {
        return tileMap.getTile(zoneType);
    }
}