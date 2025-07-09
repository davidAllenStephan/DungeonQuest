package davidmarino.dungeon.dungeonviews;

import davidmarino.dungeon.dungeonmodels.TileCanvas;
import davidmarino.DungeonQuestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class DungeonView extends DungeonQuestView {

    @Autowired
    private ZoneView zoneView = new ZoneView();

    public DungeonView() {

    }

    public String getBase64(TileCanvas tileCanvas, int[] backgroundColor, int edgeSize) {
        int width = 1280;
        int height = 1280;
        BufferedImage dungeon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2Dungeon = dungeon.createGraphics();
        setBackground(g2Dungeon, new Color(backgroundColor[0], backgroundColor[1], backgroundColor[2]), width, height);
        zoneView.drawZones(g2Dungeon, tileCanvas, edgeSize);
        g2Dungeon.dispose();
        return getBase64(dungeon);
    }

}
