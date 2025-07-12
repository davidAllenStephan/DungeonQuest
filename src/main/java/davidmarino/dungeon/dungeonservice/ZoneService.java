package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.TileCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {

    @Autowired
    private final RoomService roomService = new RoomService();

    public TileCanvas generateZones(TileCanvas tileCanvas) {
        roomService.generateRooms(tileCanvas);
        return tileCanvas;
    }
}