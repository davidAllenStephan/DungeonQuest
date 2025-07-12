package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.*;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    public void generateRooms(TileCanvas tileCanvas) {
        tileCanvas.findWall();
    }
}
