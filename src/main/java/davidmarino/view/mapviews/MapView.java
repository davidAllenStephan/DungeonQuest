package davidmarino.view.mapviews;

import davidmarino.model.mapmodels.Polygon;
import davidmarino.view.DungeonQuestView;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Component
public class MapView extends DungeonQuestView {
    public MapView() {

    }
    public BufferedImage getGraphics2D(ArrayList<Polygon> polygons, int width, int height, int[] backgroundColor, double waterLevel, double coastLevel, double whiteCapLevel) {
        BufferedImage map = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = map.createGraphics();
        setBackground(g2, new Color(backgroundColor[0], backgroundColor[1], backgroundColor[2]), width, height);
        PolygonView.drawFilledPolygons(g2, polygons, waterLevel, coastLevel, whiteCapLevel);
        g2.dispose();
        return map;
    }
}
