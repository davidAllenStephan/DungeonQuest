package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.Tile;
import davidmarino.dungeon.dungeonmodels.TileCanvas;
import davidmarino.DungeonQuestView;
import davidmarino.dungeon.dungeonmodels.enums.TileDecorationType;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class DungeonView extends DungeonQuestView {

    public DungeonView() {

    }

    public String getBase64(TileCanvas tileCanvas, int floorVariant, int topWallVariant) {
        int tileWidth = 16;
        int tileHeight = 16;

        int cols = tileCanvas.width;
        int rows = tileCanvas.height;

        int upscaleWidth = cols * tileWidth;
        int upscaleHeight = rows * tileHeight;

        BufferedImage largeImage = new BufferedImage(upscaleWidth, upscaleHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gLarge = largeImage.createGraphics();

        // 1. Draw base tile images
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Tile tile = tileCanvas.find(x, y);
                if (tile != null) {
                    BufferedImage asset = tile.getTileAsset(floorVariant, topWallVariant);
                    gLarge.drawImage(asset, x * tileWidth, y * tileHeight, null);
                }
            }
        }

        // 2. Draw decorations (assumed to be transparent PNG overlays)
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Tile tile = tileCanvas.find(x, y);
                if (tile != null && tile.decorationTileType != null && tile.decorationTileType != TileDecorationType.DECORATION_EMPTY) {
                    BufferedImage decoration = tile.getDecorationTileAsset();
                    if (decoration != null) {
                        gLarge.drawImage(decoration, x * tileWidth, y * tileHeight, null);
                    }
                }
            }
        }

        gLarge.dispose();
        return getBase64(largeImage);
    }


}
