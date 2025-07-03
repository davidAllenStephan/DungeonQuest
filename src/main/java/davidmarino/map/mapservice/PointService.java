package davidmarino.map.mapservice;

import davidmarino.map.mapmodels.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class PointService {

    /**
     * Checks if a point is within a certain distance of another point.
     * @param points is a list of all points
     * @param x
     * @param y
     * @param space is the set distance between points
     * @return {@code boolean}
     */
    private boolean withinBounds(ArrayList<Point> points, int x, int y, int space) {
        if (points.isEmpty()) return false;
        for (Point p : points) {
            boolean res = ((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y)) < (space * space);
            if (res) return true;
        }
        return false;
    }

    /**
     * Creates random points dispersed within the domain and range of the image.
     * @param count of the points created
     * @return {@code ArrayList<Point>}
     */
    public ArrayList<Point> generateRandomPoints(int count, int width, int height) {
        ArrayList<Point> points = new ArrayList<>();
        Random random = new Random();
        while (count > 0) {
            int x = random.nextInt(0, width);
            int y = random.nextInt(0, height);
            if (!withinBounds(points, x, y, 100)) {
                points.add(new Point(x, y));
                count--;
            }
        }
        return points;
    }
}
