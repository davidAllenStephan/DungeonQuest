package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.Room;
import davidmarino.dungeon.dungeonmodels.TileCanvas;
import davidmarino.dungeon.dungeonmodels.Zone;
import davidmarino.dungeon.dungeonmodels.ZoneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class ZoneService {

    @Autowired
    private final RoomService roomService = new RoomService();

    public TileCanvas generateZones(TileCanvas tileCanvas, int numberOfRooms, int minimumRoomWidth, int minimumRoomHeight, int maximumRoomWidth, int maximumRoomHeight) {
        roomService.generateRooms(tileCanvas, numberOfRooms, minimumRoomWidth, minimumRoomHeight, maximumRoomWidth, maximumRoomHeight);
        return tileCanvas;


//        ArrayList<Zone> zones = new ArrayList<>();
//        for (Room room : rooms) {
//            room.setNeighbors(rooms);
//        }
//        Random random = new Random();
//        int i = 0;
//        for (Room room : rooms) {
//            zones.add(new Zone(i, room, ZoneType.values()[random.nextInt(ZoneType.values().length-2)]));
//            i++;
//        }
//        zones.get(0).zoneType = ZoneType.ENTRANCE;
//        zones.get(1).zoneType = ZoneType.EXIT;
//        return zones;
    }
}