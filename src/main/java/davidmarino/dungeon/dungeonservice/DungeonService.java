package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.Zone;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DungeonService {
    public DungeonService() {

    }
    public ArrayList<Zone> getZones(int numberOfRooms, int minimumRoomWidth, int minimumRoomHeight, int maximumRoomWidth, int maximumRoomHeight) {
        int width = 400;
        int height = 400;
        return ZoneService.generateZones(numberOfRooms, width, height, minimumRoomWidth, minimumRoomHeight, maximumRoomWidth, maximumRoomHeight);
    }
}
