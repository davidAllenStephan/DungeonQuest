package davidmarino.view.dungeonviews;

import davidmarino.model.dungeonmodels.Room;

import java.awt.*;
import java.util.ArrayList;

public class RoomView {
    public static void drawRoom(Graphics2D g2, Room room, Color color) {
        g2.setColor(color);
        g2.fillRect((int) (room.center.x - room.xRadius), (int) (room.center.y - room.yRadius), (int) room.xRadius * 2, (int) room.yRadius * 2);
    }

    public static void drawRooms(Graphics2D g2, ArrayList<Room> rooms, Color color) {
        for (Room room : rooms) {
            drawRoom(g2, room, color);
        }
    }
}
