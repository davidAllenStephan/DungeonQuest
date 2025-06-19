package davidmarino.model;

import davidmarino.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * TODO:
 * Need to make sure center is placed so bounds don't leave the region.
 */
public class Room {
    public Point center;
    public double xRadius;
    public double yRadius;
    public ArrayList<Point> bounds;
    public HashMap<Room, Double> neighbors;

    public Room(Point center, double xRadius, double yRadius) {
        this.center = center;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        bounds = new ArrayList<>();
        bounds.add(new Point(center.x + xRadius, center.y + yRadius));
        bounds.add(new Point(center.x - xRadius, center.y + yRadius));
        bounds.add(new Point(center.x - xRadius, center.y - yRadius));
        bounds.add(new Point(center.x + xRadius, center.y - yRadius));
    }

    public Room() {
        while (true) {
            Random random = new Random();
            center = new Point(random.nextDouble(0, Parameters.width), random.nextDouble(0, Parameters.height));
            xRadius = random.nextDouble(Parameters.minRoomWidth, Parameters.maxRoomWidth);
            yRadius = random.nextDouble(Parameters.minRoomHeight, Parameters.maxRoomHeight);
            if (center.x + xRadius > Parameters.width || center.x - xRadius < 0 || center.y + yRadius > Parameters.height || center.y - yRadius < 0) {
                continue;
            }
            bounds = new ArrayList<>();
            bounds.add(new Point(center.x + xRadius, center.y + yRadius));
            bounds.add(new Point(center.x - xRadius, center.y + yRadius));
            bounds.add(new Point(center.x - xRadius, center.y - yRadius));
            bounds.add(new Point(center.x + xRadius, center.y - yRadius));
            neighbors = new HashMap<>();
            break;
        }
    }

    public void setNeighbors(ArrayList<Room> rooms) {
        for (Room room : rooms) {
            double distance = Math.sqrt(Math.pow(center.x - room.center.x,2) + Math.pow(center.y - room.center.y,2));
            if (!room.equals(this)) {
                neighbors.put(room, distance);
            }
        }
    }

    @Override
    public String toString() {
        ArrayList<Integer> n = neighbors.values().stream().map(x -> (int) Math.floor(x)).collect(Collectors.toCollection(ArrayList::new));
        return "(center=" + center + ", xRadius=" + xRadius + ", yRadius=" + yRadius + ", bounds=" + bounds + ", neighbors=" + n +")";
    }
}
