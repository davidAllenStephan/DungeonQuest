package davidmarino.controller;

import davidmarino.model.Room;
import davidmarino.model.Zone;
import davidmarino.model.ZoneType;

import java.util.ArrayList;
import java.util.Random;

public class ZoneController {

    public static ArrayList<Zone> generateZones() {
        ArrayList<Zone> zones = new ArrayList<>();
        ArrayList<Room> rooms = RoomController.generateRooms();
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