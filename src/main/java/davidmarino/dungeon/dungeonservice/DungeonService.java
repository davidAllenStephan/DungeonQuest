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
        TileCanvas tileCanvas = new TileCanvas(maximumRoomWidth, maximumRoomHeight);
        return zoneService.generateZones(tileCanvas);
    }
}
