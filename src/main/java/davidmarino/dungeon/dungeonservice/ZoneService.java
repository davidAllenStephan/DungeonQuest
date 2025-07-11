package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.TileCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {

    @Autowired
    private final RoomService roomService = new RoomService();

    public TileCanvas generateZones(TileCanvas tileCanvas, int maximumRoomWidth, int maximumRoomHeight) {
        roomService.generateRooms(tileCanvas, maximumRoomWidth, maximumRoomHeight);
        return tileCanvas;
    }
}