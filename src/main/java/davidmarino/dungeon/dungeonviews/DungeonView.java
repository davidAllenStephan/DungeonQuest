package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.Edge;
import davidmarino.dungeon.dungeonmodels.Room;
import davidmarino.dungeon.dungeonmodels.Tile;
import davidmarino.dungeon.dungeonmodels.TileCanvas;
import davidmarino.DungeonQuestView;
import davidmarino.dungeon.dungeonmodels.enums.TileDecorationType;
import davidmarino.dungeon.dungeonservice.DungeonMapService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

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
                    BufferedImage asset = tile.getTileAsset(floorVariant, topWallVariant, tileCanvas.dungeonType);
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

    public void drawRoom(Graphics2D g2, Room room, Color color) {
        g2.setColor(color);
        g2.fillRect((int) (room.center.x - room.xRadius), (int) (room.center.y - room.yRadius), (int) room.xRadius * 2, (int) room.yRadius * 2);

        Font originalFont = g2.getFont();
        Font smallFont = originalFont.deriveFont(15f);
        g2.setFont(smallFont);

        String text = room.type.toString();
        FontMetrics metrics = g2.getFontMetrics(smallFont);
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getAscent();

        int x = (int) room.center.x - textWidth / 2;
        int y = (int) room.center.y + textHeight / 2;

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        g2.setFont(originalFont);
    }

    public void drawRooms(Graphics2D g2, ArrayList<Room> rooms) {
        for (Room room : rooms) {
            drawRoom(g2, room, Color.GREEN);
        }
    }

    public void drawEdge(Graphics2D g2, Edge edge, int edgeWeight, Color color) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(edgeWeight));
        int x1 = (int) edge.from.center.x;
        int y1 = (int) edge.from.center.y;
        int x2 = (int) edge.to.center.x;
        int y2 = (int) edge.to.center.y;
        if (x1 == x2 || y1 == y2) {
            g2.drawLine(x1, y1, x2, y2);
        } else {
            int midX = x2;
            int midY = y1;
            g2.drawLine(x1, y1, midX, midY);
            g2.drawLine(midX, midY, x2, y2);
        }
    }

    public void drawEdges(Graphics2D g2, Set<Edge> edge, int edgeWeight, Color color) {
        for (Edge e : edge) {
            drawEdge(g2, e, edgeWeight, color);
        }
    }

    public String getBase64(ArrayList<Room> rooms) {
        int width = 500;
        int height = 500;
        BufferedImage dungeon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dungeon.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        setBackground(g2, Color.BLACK, width, height);

        Set<Edge> edges = DungeonMapService.primMST(rooms);
        drawEdges(g2, edges, 5, Color.BLUE);

        drawRooms(g2, rooms);

        g2.dispose();
        return getBase64(dungeon);
    }

}
