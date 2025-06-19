package davidmarino;

import davidmarino.controller.*;
import davidmarino.model.*;
import davidmarino.model.Point;
import davidmarino.model.Polygon;
import davidmarino.view.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * The {@code Draw} class is used to set up and render all objects.
 * @version 13 Jun 2025
 * @author David Marino
 */
public class Draw {

    /**
     * Renders the background.
     * @param g2 is the render library
     * @param color of the background
     */
    public void setBackground(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.fillRect(0, 0, Parameters.width, Parameters.height);
    }

    /**
     * Executes the start of the generation.
     */
    public void run() {
        BufferedImage image = new BufferedImage(Parameters.width, Parameters.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        setBackground(g2, Parameters.backgroundColor);

        ArrayList<Zone> zones = ZoneController.generateZones();
        ZoneView.drawZones(g2, zones);

        g2.dispose();
        ImageExporter.exportToPNG(image, 400, 400);
    }
}
