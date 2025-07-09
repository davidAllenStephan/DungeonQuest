package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonservice.RoomService;
import davidmarino.dungeon.dungeonmodels.Edge;
import davidmarino.dungeon.dungeonmodels.Room;
import davidmarino.dungeon.dungeonmodels.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

@Component
public class ZoneView {

    public ZoneView() {

    }

    @Autowired
    private RoomView roomView = new RoomView(new TileMap());

    public void drawZone(Graphics2D g2, Zone zone) {
        roomView.drawRoom(g2, zone.room);

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

    public void drawZones(Graphics2D g2, ArrayList<Zone> zones, int edgeWeight) {
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
