package davidmarino.view;

import davidmarino.Parameters;
import davidmarino.model.Line;
import davidmarino.model.Room;

import java.awt.*;

public class RoomView {
    public static void drawRoom(Graphics2D g2, Room room, Color color) {
        PointView.drawPoint(g2, room.center, color);
        LineView.drawLine(g2, new Line(room.bounds.get(0), room.bounds.get(1)), color);
        LineView.drawLine(g2, new Line(room.bounds.get(1), room.bounds.get(2)), color);
        LineView.drawLine(g2, new Line(room.bounds.get(2), room.bounds.get(3)), color);
        LineView.drawLine(g2, new Line(room.bounds.get(3), room.bounds.get(0)), color);
    }
}
