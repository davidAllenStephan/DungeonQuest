package davidmarino.dungeon.dungeonmodels;

import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import davidmarino.dungeon.dungeonmodels.enums.TileDecorationType;
import davidmarino.dungeon.dungeonmodels.enums.TileStructureType;
import davidmarino.dungeon.dungeonviews.TileMap;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.Random;

@Component
public class Tile {
    public int x;
    public int y;
    public TileStructureType tileStructureType;
    public TileDecorationType decorationTileType;
    public DungeonType dungeonType;

    private static final TileMap tileMap = new TileMap();

    public Tile() {
        tileStructureType = TileStructureType.FLOOR;
        decorationTileType = TileDecorationType.DECORATION_EMPTY;
        dungeonType = DungeonType.TREASURE;
    }

    public Tile(int x, int y, DungeonType dungeonType) {
        this.x = x;
        this.y = y;
        tileStructureType = TileStructureType.FLOOR;
        decorationTileType = TileDecorationType.DECORATION_EMPTY;
        this.dungeonType = dungeonType;
    }

    public BufferedImage getTileAsset(int floorVariant, int wallVariant, DungeonType dungeonType) {
        switch (tileStructureType) {
            case FLOOR:
                if (dungeonType == DungeonType.PUZZLE) {
                    Random random = new Random();
                    return tileMap.getFloorTile(random.nextInt(0, 10));
                } else {
                    return tileMap.getFloorTile(floorVariant);
                }
            case WALL: return tileMap.getWallTile(wallVariant);
            case TOP_WALL: return tileMap.getTopWallTile(wallVariant);
            case EMPTY: return null;
            default: return tileMap.getBorderTile(tileStructureType);
        }
    }

    public BufferedImage getDecorationTileAsset() {
        return tileMap.getDecorationTile(dungeonType, decorationTileType);
    }
}