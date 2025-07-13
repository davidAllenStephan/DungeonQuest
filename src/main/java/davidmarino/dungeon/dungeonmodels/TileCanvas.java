package davidmarino.dungeon.dungeonmodels;

import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import org.springframework.stereotype.Component;

@Component
public class TileCanvas {
    private Tile[] tiles;
    public int width;
    public int height;
    public DungeonType dungeonType;

    public TileCanvas() {

    }

    public TileCanvas(int width, int height, DungeonType dungeonType) {
        this.dungeonType = dungeonType;
        this.width = width + 2;
        this.height = height + 3;
        this.tiles = new Tile[this.width * this.height];
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                tiles[index(x, y)] = new Tile(x, y, dungeonType);
            }
        }
    }

    private int index(int x, int y) {
        return y * width + x;
    }

    public Tile find(int x, int y) {
        return tiles[index(x, y)];
    }

}