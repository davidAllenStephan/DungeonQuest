package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.Tile;
import davidmarino.dungeon.dungeonmodels.TileCanvas;
import davidmarino.DungeonQuestView;
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
        int rows = tileCanvas.height;

        int imageWidth = cols * tileWidth;
        int imageHeight = rows * tileHeight;

        BufferedImage dungeonImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dungeonImage.createGraphics();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Tile tile = tileCanvas.find(x, y);
                g.drawImage(tile.getTileAsset(), x * tileWidth, y * tileHeight, null);
            }
        }

        g.dispose();
        return getBase64(dungeonImage);
    }

}
