package davidmarino.model;

public class Room {
    public Point center;
    public double xRadius;
    public double yRadius;

    public Room(Point center, double xRadius, double yRadius) {
        this.center = center;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
    }

    public double getWidth() {
        return center.x + xRadius;
    }

    public double getHeight() {
        return center.y + yRadius;
    }

}
