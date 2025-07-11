package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.TileCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DungeonService {

    @Autowired
    private final ZoneService zoneService = new ZoneService();

    public DungeonService() {

    }

    public TileCanvas getZones(int maximumRoomWidth, int maximumRoomHeight) {
        int width = maximumRoomWidth;
        int height = maximumRoomHeight;
        TileCanvas tileCanvas = new TileCanvas(width, height);
        return zoneService.generateZones(tileCanvas, maximumRoomWidth, maximumRoomHeight);
    }
}
