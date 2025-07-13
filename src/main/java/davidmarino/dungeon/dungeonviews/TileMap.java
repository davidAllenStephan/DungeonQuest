package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import davidmarino.dungeon.dungeonmodels.enums.TileDecorationType;
import davidmarino.dungeon.dungeonmodels.enums.TileStructureType;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@Component
public final class TileMap {

    private final Map<TileStructureType, TileReference> structureTileReferences = new HashMap<>();
    private final Map<DungeonType, Map<TileDecorationType, TileReference>> decorationTileReferences = new HashMap<>();
    private final List<TileReference> floorTileVariants = new ArrayList<>();
    private final List<TileReference> wallTileVariants = new ArrayList<>();
    private final List<TileReference> topWallTileVariants = new ArrayList<>();

    private BufferedImage floorSheet;
    private BufferedImage wallSheet;
    private BufferedImage borderSheet;
    private BufferedImage decorationSheet;

    private void loadTileSheets() {
        try {
            floorSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/Room_Builder_Floors_16x16.png")));
            wallSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/Room_Builder_Walls_16x16.png")));
            borderSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/Room_Builder_borders_16x16.png")));
            decorationSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/Interiors_16x16.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFloorTiles() {
        int floorTileWidth = floorSheet.getWidth() / 16;
        int floorTileHeight = floorSheet.getHeight() / 16;
        for (int y = 3; y < floorTileHeight; y += 2) {
            for (int x = 1; x < floorTileWidth; x += 4) {
                floorTileVariants.add(new TileReference(floorSheet, x, y));
            }
        }
        if (!floorTileVariants.isEmpty()) {
            structureTileReferences.put(TileStructureType.FLOOR, floorTileVariants.get(0));
        }
    }

    private void loadWallTiles() {
        int wallTileWidth = wallSheet.getWidth() / 16;
        int wallTileHeight = wallSheet.getHeight() / 16;

        for (int y = 0; y < wallTileHeight; y += 2) {
            for (int x : new int[]{1, 12, 23}) {
                if (x < wallTileWidth) {
                    topWallTileVariants.add(new TileReference(wallSheet, x, y));
                }
            }
        }

        for (int y = 0; y < wallTileHeight; y += 2) {
            for (int x : new int[]{1, 12, 23}) {
                if (x < wallTileWidth) {
                    wallTileVariants.add(new TileReference(wallSheet, x, y + 1));
                }
            }
        }

        if (!topWallTileVariants.isEmpty()) {
            structureTileReferences.put(TileStructureType.TOP_WALL, topWallTileVariants.get(0));
        }

        if (!wallTileVariants.isEmpty()) {
            structureTileReferences.put(TileStructureType.WALL, wallTileVariants.get(0));
        }
    }


    private void loadBorderTiles() {
        structureTileReferences.put(TileStructureType.FRONT_BORDER, new TileReference(borderSheet, 7, 8));
        structureTileReferences.put(TileStructureType.LEFT_BORDER, new TileReference(borderSheet, 2, 5));
        structureTileReferences.put(TileStructureType.RIGHT_BORDER, new TileReference(borderSheet, 3, 5));
        structureTileReferences.put(TileStructureType.TOP_LEFT_CORNER_BORDER, new TileReference(borderSheet, 6, 6));
        structureTileReferences.put(TileStructureType.TOP_RIGHT_CORNER_BORDER, new TileReference(borderSheet, 8, 6));
        structureTileReferences.put(TileStructureType.FRONT_LEFT_CORNER_BORDER, new TileReference(borderSheet, 6, 8));
        structureTileReferences.put(TileStructureType.FRONT_RIGHT_CORNER_BORDER, new TileReference(borderSheet, 8, 8));
        structureTileReferences.put(TileStructureType.RIGHT_CONNECTING_BORDER, new TileReference(borderSheet, 5, 6));
        structureTileReferences.put(TileStructureType.LEFT_CONNECTING_BORDER, new TileReference(borderSheet, 4, 6));
    }

    private void loadDecorationTiles() {
        Map<TileDecorationType, TileReference> healthReferences = new HashMap<>();
        healthReferences.put(TileDecorationType.DECORATION_COMMON, new TileReference(decorationSheet, 6, 80));
        healthReferences.put(TileDecorationType.DECORATION_UNCOMMON, new TileReference(decorationSheet, 13, 544));
        healthReferences.put(TileDecorationType.DECORATION_RARE, new TileReference(decorationSheet, 5, 1062));
        healthReferences.put(TileDecorationType.DECORATION_LEGENDARY, new TileReference(decorationSheet, 3, 451));
        healthReferences.put(TileDecorationType.DECORATION_MYSTICAL, new TileReference(decorationSheet, 6, 80));
        decorationTileReferences.put(DungeonType.HEALTH, healthReferences);
    }

    public TileMap() {
        loadTileSheets();
        loadFloorTiles();
        loadWallTiles();
        loadBorderTiles();
        loadDecorationTiles();
    }

    public BufferedImage getBorderTile(TileStructureType tileStructureType) {
        TileReference ref = structureTileReferences.get(tileStructureType);
        if (ref == null) {
            throw new IllegalArgumentException("Tile type not found: " + tileStructureType);
        }
        return getTileFromSheet(ref.sheet, ref.x, ref.y, 1, 1);
    }

    public BufferedImage getDecorationTile(DungeonType dungeonType, TileDecorationType tileDecorationType) {
        TileReference ref = decorationTileReferences.get(dungeonType).get(tileDecorationType);
        if (ref == null) {
            throw new IllegalArgumentException("Tile type not found: " + dungeonType);
        }
        return getTileFromSheet(ref.sheet, ref.x, ref.y, 1, 1);
    }

    public BufferedImage getFloorTile(int variantIndex) {
        if (variantIndex < 0 || variantIndex >= floorTileVariants.size()) {
            throw new IndexOutOfBoundsException("Invalid floor variant index: " + variantIndex);
        }
        TileReference ref = floorTileVariants.get(variantIndex);
        return getTileFromSheet(ref.sheet, ref.x, ref.y, 1, 1);
    }

    public BufferedImage getTopWallTile(int variantIndex) {
        if (variantIndex < 0 || variantIndex >= topWallTileVariants.size()) {
            throw new IndexOutOfBoundsException("Invalid wall variant index: " + variantIndex);
        }
        TileReference ref = topWallTileVariants.get(variantIndex);
        return getTileFromSheet(ref.sheet, ref.x, ref.y, 1, 1);
    }

    public BufferedImage getWallTile(int variantIndex) {
        if (variantIndex < 0 || variantIndex >= wallTileVariants.size()) {
            throw new IndexOutOfBoundsException("Invalid wall variant index: " + variantIndex);
        }
        TileReference ref = wallTileVariants.get(variantIndex);
        return getTileFromSheet(ref.sheet, ref.x, ref.y, 1, 1);
    }

    private BufferedImage getTileFromSheet(BufferedImage sheet, int x, int y, int width, int height) {
        return sheet.getSubimage(x * 16, y * 16, 16 * width, 16 * height);
    }

    private static class TileReference {
        BufferedImage sheet;
        int x, y;

        TileReference(BufferedImage sheet, int x, int y) {
            this.sheet = sheet;
            this.x = x;
            this.y = y;
        }
    }
}