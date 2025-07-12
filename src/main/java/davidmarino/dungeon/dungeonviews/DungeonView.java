package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.Tile;
import davidmarino.dungeon.dungeonmodels.TileCanvas;
import davidmarino.DungeonQuestView;
import davidmarino.dungeon.dungeonmodels.TileType;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class DungeonView extends DungeonQuestView {

    public DungeonView() {

    }

    public String getBase64(TileCanvas tileCanvas) {
        int tileWidth = 16;
        int tileHeight = 16;

        int cols = tileCanvas.width;
        int rows = tileCanvas.height + 1;

        int upscaleWidth = cols * tileWidth;
        int upscaleHeight = rows * tileHeight;

        BufferedImage largeImage = new BufferedImage(upscaleWidth, upscaleHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gLarge = largeImage.createGraphics();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Tile tile = tileCanvas.find(x, y);
                if (tile != null) {
                    BufferedImage asset = tile.getTileAsset();
                    gLarge.drawImage(asset, x * tileWidth, y * tileHeight, null); // invert Y if needed
                }
            }
        }

        gLarge.dispose();

        return getBase64(largeImage);
    }

}
