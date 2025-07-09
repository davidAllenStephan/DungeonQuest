package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Component
public class RoomView {

    private final TileMap tileMap;

    @Autowired
    public RoomView(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public void drawRoom(Graphics2D g2, Room room) {
        int tileSize = 16;

        int left = (int) (Math.floor((room.center.x - room.xRadius) / tileSize) * tileSize);
        int top = (int) (Math.floor((room.center.y - room.yRadius) / tileSize) * tileSize);
        int width = (int) (Math.ceil(room.xRadius * 2 / tileSize) * tileSize);
        int height = (int) (Math.ceil(room.yRadius * 2 / tileSize) * tileSize);

        int tilesX = width / tileSize;
        int tilesY = height / tileSize;

        // First draw: corners, top wall, floor (not front/bottom wall yet)
        for (int y = 0; y < tilesY; y++) {
            for (int x = 0; x < tilesX; x++) {
                int drawX = left + x * tileSize;
                int drawY = top + y * tileSize;
                BufferedImage tile;

                boolean isCorner = (x == 0 && y == 0) || (x == tilesX - 1 && y == 0) ||
                        (x == 0 && y == tilesY - 1) || (x == tilesX - 1 && y == tilesY - 1);

                if (isCorner) {
                    if (x == 0 && y == 0)
                        tile = tileMap.getTile("topLeft");
                    else if (x == tilesX - 1 && y == 0)
                        tile = tileMap.getTile("topRight");
                    else if (x == 0 && y == tilesY - 1)
                        tile = tileMap.getTile("bottomLeft");
                    else
                        tile = tileMap.getTile("bottomRight");
                    g2.drawImage(tile, drawX, drawY, null);
                } else if (y == 0) {
                    // Top wall only
                    tile = tileMap.getTile("wall");
                    g2.drawImage(tile, drawX, drawY, null);
                } else if (y != tilesY - 1) {
                    // Floor
                    tile = tileMap.getTile("floor");
                    g2.drawImage(tile, drawX, drawY, null);
                }
            }
        }

        // Draw bottom (front) wall on top of corners, shifted down
        int frontWallY = top + (tilesY - 1) * tileSize + 1; // +1 for better visual overlap
        for (int x = 1; x < tilesX - 1; x++) { // skip corners
            int drawX = left + x * tileSize;
            BufferedImage wallTile = tileMap.getTile("wall");
            g2.drawImage(wallTile, drawX, frontWallY, null);
        }
    }



    public void drawRooms(Graphics2D g2, ArrayList<Room> rooms, Color color) {
        for (Room room : rooms) {
            drawRoom(g2, room);
        }
    }
}
