package davidmarino.dungeon.dungeonmodels;

import org.springframework.stereotype.Component;

@Component
public class TileCanvas {
    private Tile[] tiles;
    public int width;
    public int height;

    public TileCanvas() {

    }

    public TileCanvas(int width, int height) {
        this.width = width;
        this.height = height;

        int totalRows = height + 2; // +1 top wall, +1 front wall
        this.tiles = new Tile[width * totalRows];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < totalRows; y++) {
                tiles[index(x, y)] = new Tile(x, y);
            }
        }
    }

    // Adjusted to match new total height
    private int index(int x, int y) {
        return y * width + x;
    }

    public Tile find(int x, int y) {
        return tiles[index(x, y)];
    }

    public void findWall() {
        int topEdgeY = 0;               // top edge is now y=0
        int topWallY = 1;               // wall just below top edge

        // Top edge and wall row
        for (int x = 0; x < width; x++) {
            Tile edgeTile = find(x, topEdgeY);
            Tile wallTile = find(x, topWallY);
            edgeTile.tileType = TileType.TOP_WALL;
            wallTile.tileType = TileType.WALL;
        }

        // Front border (bottom row)
        int frontBorderY = height - 1;  // bottom row
        for (int x = 0; x < width; x++) {
            Tile edgeTile = find(x, frontBorderY);
            edgeTile.tileType = TileType.FRONT_BORDER;
        }

        // Left and right borders for all rows between top and front border
        for (int y = 1; y < height - 1; y++) {
            Tile leftTile = find(0, y);
            Tile rightTile = find(width - 1, y);
            leftTile.tileType = TileType.LEFT_BORDER;
            rightTile.tileType = TileType.RIGHT_BORDER;
        }
    }

}