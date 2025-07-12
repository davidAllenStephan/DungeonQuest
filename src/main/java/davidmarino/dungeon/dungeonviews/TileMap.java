package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.TileType;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public final class TileMap {

    private final Map<TileType, BufferedImage> tiles = new HashMap<>();

    public BufferedImage getTile(TileType tileType) {
        return tiles.get(tileType);
    }

    private BufferedImage getTileFromSheet(BufferedImage sheet, int x, int y) {
        return sheet.getSubimage(x * 16, y * 16, 16, 16);
    }

    public TileMap() {
        try {
            BufferedImage floorSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/Room_Builder_Floors_16x16.png")));
            BufferedImage wallSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/Room_Builder_Walls_16x16.png")));
            BufferedImage borderSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/Room_Builder_borders_16x16.png")));

            Map<TileType, Point> floorTiles = Map.of(
                    TileType.FLOOR, new Point(1, 3)
            );

            Map<TileType, Point> wallTiles = Map.of(
                    TileType.WALL, new Point(1, 1),
                    TileType.TOP_WALL, new Point(1, 0)  // example coord, update as needed
            );

            Map<TileType, Point> borderTiles = Map.of(
                    TileType.FRONT_BORDER, new Point(4, 3),  // update with correct coords
                    TileType.LEFT_BORDER, new Point(3, 0),
                    TileType.RIGHT_BORDER, new Point(2, 0)
            );

            // Load floor tiles
            for (Map.Entry<TileType, Point> entry : floorTiles.entrySet()) {
                Point p = entry.getValue();
                tiles.put(entry.getKey(), getTileFromSheet(floorSheet, p.x, p.y));
            }

            // Load wall tiles
            for (Map.Entry<TileType, Point> entry : wallTiles.entrySet()) {
                Point p = entry.getValue();
                tiles.put(entry.getKey(), getTileFromSheet(wallSheet, p.x, p.y));
            }

            // Load border tiles
            for (Map.Entry<TileType, Point> entry : borderTiles.entrySet()) {
                Point p = entry.getValue();
                tiles.put(entry.getKey(), getTileFromSheet(borderSheet, p.x, p.y));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load tile sheets", e);
        }
    }

    private static class Point {
        int x;
        int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
