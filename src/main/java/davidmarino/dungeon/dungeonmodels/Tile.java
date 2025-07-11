package davidmarino.dungeon.dungeonmodels;

import davidmarino.dungeon.dungeonviews.TileMap;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class Tile {
    public int x;
    public int y;
    public TileType tileType;
    public RoomBuilderType roomBuilderType;

    private static final TileMap tileMap = new TileMap();

    public Tile() {
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.roomBuilderType = RoomBuilderType.SITE;
    }

    public Tile(int x, int y, RoomBuilderType roomBuilderType) {
        this.x = x;
        this.y = y;
        this.roomBuilderType = roomBuilderType;
    }

    public BufferedImage getTileAsset() {
        return tileMap.getTile(tileType);
    }
}