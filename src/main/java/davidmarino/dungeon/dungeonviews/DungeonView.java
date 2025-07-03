package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.Zone;
import davidmarino.DungeonQuestView;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Service
public class DungeonView extends DungeonQuestView {

    public DungeonView() {

    }

    public String getBase64(ArrayList<Zone> zones, int[] backgroundColor, int edgeSize) {
        int width = 500;
        int height = 500;
        BufferedImage dungeon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2Dungeon = dungeon.createGraphics();
        setBackground(g2Dungeon, new Color(backgroundColor[0], backgroundColor[1], backgroundColor[2]), width, height);
        ZoneView.drawZones(g2Dungeon, zones, edgeSize);
        g2Dungeon.dispose();
        return getBase64(dungeon);
    }

}
