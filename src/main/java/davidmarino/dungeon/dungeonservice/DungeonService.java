package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.Tile;
import davidmarino.dungeon.dungeonmodels.TileCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DungeonService {

    @Autowired
    private final ZoneService zoneService = new ZoneService();

    public DungeonService() {

    }

    public TileCanvas getZones(int numberOfRooms, int minimumRoomWidth, int minimumRoomHeight, int maximumRoomWidth, int maximumRoomHeight) {
        int width = 540;
        int height = 540;
        Tile[][] tiles = new Tile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = new Tile(x, y);
            }
        }
        TileCanvas tileCanvas = new TileCanvas(tiles);
        return zoneService.generateZones(tileCanvas, numberOfRooms, minimumRoomWidth, minimumRoomHeight, maximumRoomWidth, maximumRoomHeight);
    }
}
