package davidmarino.service;

import davidmarino.model.Room;
import davidmarino.model.Zone;
import davidmarino.model.ZoneType;

import java.util.ArrayList;
import java.util.Random;

public class ZoneService {

    public static ArrayList<Zone> generateZones(int numberOfRooms, int width, int height, int minimumRoomWidth, int minimumRoomHeight, int maximumRoomWidth, int maximumRoomHeight) {
        ArrayList<Zone> zones = new ArrayList<>();
        ArrayList<Room> rooms = RoomService.generateRooms(numberOfRooms, width, height, minimumRoomWidth, minimumRoomHeight, maximumRoomWidth, maximumRoomHeight);
        for (Room room : rooms) {
            room.setNeighbors(rooms);
        }
        Random random = new Random();
        int i = 0;
        for (Room room : rooms) {
            zones.add(new Zone(i, room, ZoneType.values()[random.nextInt(ZoneType.values().length)]));
            i++;
        }
        return zones;
    }
}