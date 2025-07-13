package davidmarino.dungeon.dungeonmodels;

import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import davidmarino.dungeon.dungeonmodels.enums.TileDecorationType;
import davidmarino.dungeon.dungeonmodels.enums.TileStructureType;
import davidmarino.dungeon.dungeonviews.TileMap;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

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
        dungeonType = DungeonType.EMPTY;
    }

    public Tile(int x, int y, DungeonType dungeonType) {
        this.x = x;
        this.y = y;
        tileStructureType = TileStructureType.FLOOR;
        decorationTileType = TileDecorationType.DECORATION_EMPTY;
        this.dungeonType = dungeonType;
    }

    public BufferedImage getTileAsset(int floorVariant, int wallVariant) {
        return switch (tileStructureType) {
            case FLOOR -> tileMap.getFloorTile(floorVariant);
            case WALL -> tileMap.getWallTile(wallVariant);
            case TOP_WALL -> tileMap.getTopWallTile(wallVariant);
            case EMPTY -> null;
            default -> tileMap.getBorderTile(tileStructureType);
        };
    }

    public BufferedImage getDecorationTileAsset() {
        return tileMap.getDecorationTile(dungeonType, decorationTileType);
    }
}