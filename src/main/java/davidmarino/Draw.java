package davidmarino;

import davidmarino.controller.LineController;
import davidmarino.controller.PointController;
import davidmarino.controller.PolygonController;
import davidmarino.controller.RoomController;
import davidmarino.model.Line;
import davidmarino.model.Point;
import davidmarino.model.Polygon;
import davidmarino.model.Room;
import davidmarino.view.LineView;
import davidmarino.view.PolygonView;
import davidmarino.view.RoomView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

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

        Room roomA = new Room(new Point(200, 200), 6, 10);
        Room roomB = new Room(new Point(250, 300), 10, 23);

        RoomView.drawRoom(g2, roomA, Color.RED);
        RoomView.drawRoom(g2, roomB, Color.BLUE);

        LineView.drawLine(g2, new Line(roomA.center, roomB.center), Color.BLACK);

        System.out.println(RoomController.getDistance(roomA, roomB));


        g2.dispose();
        ImageExporter.exportToPNG(image, 400, 400);
    }
}
