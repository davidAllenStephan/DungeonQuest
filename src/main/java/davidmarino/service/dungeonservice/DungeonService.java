package davidmarino.service.dungeonservice;

import davidmarino.model.Parameters;
import davidmarino.model.dungeonmodels.Zone;
import davidmarino.service.DungeonQuestService;
import davidmarino.view.dungeonviews.ZoneView;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Component
public class DungeonService extends DungeonQuestService {

    public DungeonService(Parameters parameters) {
        super(parameters);
    }

    public byte[] runDungeon() {
        BufferedImage dungeon = new BufferedImage(parameters.width, parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2Dungeon = dungeon.createGraphics();

        setBackground(g2Dungeon, new Color(parameters.backgroundColor[0], parameters.backgroundColor[1], parameters.backgroundColor[2]));

        ArrayList<Zone> zones = ZoneService.generateZones(parameters.numberOfRooms, parameters.width, parameters.height, parameters.minimumRoomWidth, parameters.minimumRoomHeight, parameters.maximumRoomWidth, parameters.maximumRoomHeight);
        ZoneView.drawZones(g2Dungeon, zones, parameters.edgeSize);

        g2Dungeon.dispose();
        return getBytes(dungeon);
    }

    public ArrayList<Zone> getZones() {
        ArrayList<Zone> zones = ZoneService.generateZones(parameters.numberOfRooms, parameters.width, parameters.height, parameters.minimumRoomWidth, parameters.minimumRoomHeight, parameters.maximumRoomWidth, parameters.maximumRoomHeight);
        return zones;
    }

    public String getBase64(ArrayList<Zone> zones) {
        BufferedImage dungeon = new BufferedImage(parameters.width, parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2Dungeon = dungeon.createGraphics();
        setBackground(g2Dungeon, new Color(parameters.backgroundColor[0], parameters.backgroundColor[1], parameters.backgroundColor[2]));
        ZoneView.drawZones(g2Dungeon, zones, parameters.edgeSize);
        g2Dungeon.dispose();
        return getBase64(dungeon);
    }

}
