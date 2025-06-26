package davidmarino.model.dungeonmodels;

import java.awt.*;

public class Zone {
    public int id;
    public Room room;

    public ZoneType zoneType;
    public Color color;

    public Zone(int id, Room room, ZoneType zoneType) {
        this.id = id;
        this.room = room;
        this.zoneType = zoneType;
        setColor(zoneType);
    }

    private void setColor(ZoneType zoneType) {
        switch (zoneType) {
            case ROOM:      color = new Color(200, 200, 200); break;
            case CORRIDOR:  color = new Color(120, 120, 120); break;
            case TREASURE:  color = new Color(255, 215, 0); break;
            case BOSS:      color = new Color(128, 0, 128); break;
            case PUZZLE:    color = new Color(0, 191, 255); break;
            case SAFE:      color = new Color(135, 206, 250); break;
            case SECRET:    color = new Color(255, 105, 180); break;
            case VOID:      color = new Color(30, 30, 30); break;
            case ENTRANCE:  color = new Color(0, 128, 0); break;
            case EXIT:      color = new Color(255, 0, 0); break;
        }
    }

}
