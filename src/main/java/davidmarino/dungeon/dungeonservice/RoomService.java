package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.*;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    public void generateRooms(TileCanvas tileCanvas, int maximumRoomWidth, int maximumRoomHeight) {
        int x = tileCanvas.width / 2;
        int y = tileCanvas.height / 2;
        tileCanvas.find(x, y).roomBuilderType = RoomBuilderType.SITE;
        tileCanvas.toZone(tileCanvas.find(x, y), maximumRoomWidth, maximumRoomHeight, RoomBuilderType.ZONE);
    }
}
