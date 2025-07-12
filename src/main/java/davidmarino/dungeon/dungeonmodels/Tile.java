package davidmarino.dungeon.dungeonmodels;

import davidmarino.dungeon.dungeonviews.TileMap;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class Tile {
    public int x;
    public int y;
    public TileType tileType;

    private static final TileMap tileMap = new TileMap();

    public Tile() {
        tileType = TileType.FLOOR;
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        tileType = TileType.FLOOR;
    }

    public Tile(int x, int y, TileType tileType) {
        this.x = x;
        this.y = y;
        this.tileType = tileType;
    }

    public BufferedImage getTileAsset() {
        return tileMap.getTile(tileType);
    }
}