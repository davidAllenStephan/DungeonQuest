package davidmarino.view.dungeonviews;

import davidmarino.model.dungeonmodels.Zone;
import davidmarino.view.DungeonQuestView;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Service
public class DungeonView extends DungeonQuestView {

    public DungeonView() {

    }

    public String getBase64(ArrayList<Zone> zones, int[] backgroundColor, int edgeSize) {
        int width = 400;
        int height = 400;
        BufferedImage dungeon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2Dungeon = dungeon.createGraphics();
        setBackground(g2Dungeon, new Color(backgroundColor[0], backgroundColor[1], backgroundColor[2]), width, height);
        ZoneView.drawZones(g2Dungeon, zones, edgeSize);
        g2Dungeon.dispose();
        return getBase64(dungeon);
    }

}
