package davidmarino.model;

import java.util.ArrayList;

public class Room {
    public Point center;
    public double xRadius;
    public double yRadius;
    public ArrayList<Point> bounds;

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

    @Override
    public String toString() {
        return "(center=" + center + ", xRadius=" + xRadius + ", yRadius=" + yRadius + ", bounds=" + bounds + ")";
    }
}
