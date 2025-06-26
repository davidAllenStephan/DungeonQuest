package davidmarino.view.dungeonviews;

import davidmarino.service.dungeonservice.RoomService;
import davidmarino.model.dungeonmodels.Edge;
import davidmarino.model.dungeonmodels.Room;
import davidmarino.model.dungeonmodels.Zone;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class ZoneView {
    public static void drawZone(Graphics2D g2, Zone zone) {
        RoomView.drawRoom(g2, zone.room, zone.color);

        Font originalFont = g2.getFont();
        Font smallFont = originalFont.deriveFont(10f);
        g2.setFont(smallFont);

        String text = zone.zoneType.toString();
        FontMetrics metrics = g2.getFontMetrics(smallFont);
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getAscent();

        int x = (int) zone.room.center.x - textWidth / 2;
        int y = (int) zone.room.center.y + textHeight / 2;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        g2.setFont(originalFont);
    }

    public static void drawZones(Graphics2D g2, ArrayList<Zone> zones, int edgeWeight) {
        ArrayList<Room> rooms = new ArrayList<>();
        for (Zone zone : zones) {
            rooms.add(zone.room);
        }
        Set<Edge> edges = RoomService.primMST(rooms);
        EdgeView.drawEdges(g2, edges, edgeWeight, Color.BLACK);

        for (Zone zone : zones) {
            drawZone(g2, zone);
        }
    }
}
