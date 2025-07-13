package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.*;
import davidmarino.dungeon.dungeonmodels.enums.DungeonShape;
import davidmarino.dungeon.dungeonmodels.enums.TileDecorationType;
import davidmarino.dungeon.dungeonmodels.enums.TileStructureType;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TileCanvasService {

    private TileCanvas tileCanvas;
    private DungeonShape dungeonShape;

    public TileCanvasService(TileCanvas tileCanvas, DungeonShape dungeonShape) {
        this.tileCanvas = tileCanvas;
        this.dungeonShape = dungeonShape;
    }

    public TileCanvasService() {

    }

    public void setTileCanvas(TileCanvas tileCanvas) {
        this.tileCanvas = tileCanvas;
    }

    public void setDungeonShape(DungeonShape dungeonShape) {
        this.dungeonShape = dungeonShape;
    }

    public void setFloors() {
        final int width = tileCanvas.width;
        final int height = tileCanvas.height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = tileCanvas.find(x, y);
                if (!isInRoom(x, y)) {
                    tile.tileStructureType = TileStructureType.EMPTY;
                    continue;
                }
                if (x > 0 && x < width - 1 && y > 1 && y < height - 1) {
                    tile.tileStructureType = TileStructureType.FLOOR;
                }
            }
        }
    }

    public void setWalls() {
        final int width = tileCanvas.width;
        final int height = tileCanvas.height;
        int midX = width / 2;
        int midY = (height / 2) - 1;

        // TOP WALL, WALL, AND FRONT BORDER, BASIC SQUARE
        for (int x = 1; x < width - 1; x++) {
            if (isInRoom(x, 1)) {
                tileCanvas.find(x, 1).tileStructureType = TileStructureType.WALL;
            }
            if (isInRoom(x, 0)) {
                tileCanvas.find(x, 0).tileStructureType = TileStructureType.TOP_WALL;
            }
            if (isInRoom(x, height - 1)) {
                tileCanvas.find(x, height - 1).tileStructureType = TileStructureType.FRONT_BORDER;
            }
        }

        // ADJUSTING TO SHAPE DEFAULT IS SQUARE
        if (dungeonShape == DungeonShape.L_BOTTOM_LEFT) {
            for (int x = midX; x < width - 1; x++) {
                tileCanvas.find(x, midY).tileStructureType = TileStructureType.WALL;
                tileCanvas.find(x, midY - 1).tileStructureType = TileStructureType.TOP_WALL;
            }
            for (int y = 1; y < midY - 1; y++) {
                tileCanvas.find(midX, y).tileStructureType = TileStructureType.RIGHT_BORDER;
            }
            tileCanvas.find(midX, 0).tileStructureType = TileStructureType.TOP_RIGHT_CORNER_BORDER;
            tileCanvas.find(width - 1, midY).tileStructureType = TileStructureType.RIGHT_BORDER;
            tileCanvas.find(width - 1, midY - 1).tileStructureType = TileStructureType.TOP_RIGHT_CORNER_BORDER;
        }

        else if (dungeonShape == DungeonShape.L_BOTTOM_RIGHT) {
            for (int x = 1; x < midX; x++) {
                tileCanvas.find(x, midY).tileStructureType = TileStructureType.WALL;
                tileCanvas.find(x, midY - 1).tileStructureType = TileStructureType.TOP_WALL;
            }
            for (int y = 1; y < midY - 1; y++) {
                tileCanvas.find(midX - 1, y).tileStructureType = TileStructureType.LEFT_BORDER;
            }
            tileCanvas.find(midX - 1, 0).tileStructureType = TileStructureType.TOP_LEFT_CORNER_BORDER;
            tileCanvas.find(0, midY).tileStructureType = TileStructureType.LEFT_BORDER;
            tileCanvas.find(0, midY - 1).tileStructureType = TileStructureType.TOP_LEFT_CORNER_BORDER;
        }

        else if (dungeonShape == DungeonShape.L_TOP_LEFT) {
            for (int x = midX; x < width - 1; x++) {
                tileCanvas.find(x, midY + 1).tileStructureType = TileStructureType.FRONT_BORDER;
            }
            for (int y = midY + 1; y < height - 1; y++) {
                tileCanvas.find(midX, y).tileStructureType = TileStructureType.RIGHT_BORDER;
            }
            tileCanvas.find(midX, height - 1).tileStructureType = TileStructureType.FRONT_RIGHT_CORNER_BORDER;
            tileCanvas.find(width - 1, midY + 1).tileStructureType = TileStructureType.FRONT_RIGHT_CORNER_BORDER;
            tileCanvas.find(midX, midY + 1).tileStructureType = TileStructureType.RIGHT_CONNECTING_BORDER;
        }

        else if (dungeonShape == DungeonShape.L_TOP_RIGHT) {
            for (int x = 0; x < midX; x++) {
                tileCanvas.find(x, midY + 1).tileStructureType = TileStructureType.FRONT_BORDER;
            }
            for (int y = midY + 1; y < height - 1; y++) {
                tileCanvas.find(midX, y).tileStructureType = TileStructureType.LEFT_BORDER;
            }
            tileCanvas.find(midX, height - 1).tileStructureType = TileStructureType.FRONT_LEFT_CORNER_BORDER;
            tileCanvas.find(0, midY + 1).tileStructureType = TileStructureType.FRONT_LEFT_CORNER_BORDER;
            tileCanvas.find(midX, midY + 1).tileStructureType = TileStructureType.LEFT_CONNECTING_BORDER;
        }
    }

    public void setBorders() {
        final int width = tileCanvas.width;
        final int height = tileCanvas.height;

        // LEFT AND RIGHT BORDER
        for (int y = 1; y < height - 1; y++) {
            if (isInRoom(0, y)) {
                tileCanvas.find(0, y).tileStructureType = TileStructureType.LEFT_BORDER;
            }
            if (isInRoom(width - 1, y)) {
                tileCanvas.find(width - 1, y).tileStructureType = TileStructureType.RIGHT_BORDER;
            }
        }

        // CORNERS
        if (isInRoom(0, 0)) {
            tileCanvas.find(0, 0).tileStructureType = TileStructureType.TOP_LEFT_CORNER_BORDER;
        }
        if (isInRoom(width - 1, 0)) {
            tileCanvas.find(width - 1, 0).tileStructureType = TileStructureType.TOP_RIGHT_CORNER_BORDER;
        }
        if (isInRoom(0, height - 1)) {
            tileCanvas.find(0, height - 1).tileStructureType = TileStructureType.FRONT_LEFT_CORNER_BORDER;
        }
        if (isInRoom(width - 1, height - 1)) {
            tileCanvas.find(width - 1, height - 1).tileStructureType = TileStructureType.FRONT_RIGHT_CORNER_BORDER;
        }
    }

    public void setDecorations() {
        final int width = tileCanvas.width;
        final int height = tileCanvas.height;
        Random random = new Random();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = tileCanvas.find(x, y);
                if (tile.tileStructureType == TileStructureType.FLOOR) {
                    if (random.nextDouble() < 0.2) {
                        double rarity = random.nextDouble();
                        if (rarity < 0.5) {
                            tile.decorationTileType = TileDecorationType.DECORATION_COMMON;
                        } else if (rarity < 0.7) {
                            tile.decorationTileType = TileDecorationType.DECORATION_UNCOMMON;
                        } else if (rarity < 0.85) {
                            tile.decorationTileType = TileDecorationType.DECORATION_RARE;
                        } else if (rarity < 0.95) {
                            tile.decorationTileType = TileDecorationType.DECORATION_LEGENDARY;
                        } else {
                            tile.decorationTileType = TileDecorationType.DECORATION_MYSTICAL;
                        }
                    }
                }
            }
        }
    }


    private boolean isInRoom(int x, int y) {
        final int width = tileCanvas.width;
        final int height = tileCanvas.height;
        int midX = width / 2;
        int midY = height / 2;

        return switch (dungeonShape) {
            case SQUARE -> true;
            case L_TOP_LEFT -> (x < midX || y < midY);
            case L_TOP_RIGHT -> (x >= midX || y < midY);
            case L_BOTTOM_LEFT -> (x < midX || y >= midY);
            case L_BOTTOM_RIGHT -> (x >= midX || y >= midY);
            default -> false;
        };
    }
}
