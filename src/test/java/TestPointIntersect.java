import davidmarino.GeometryUtils;
import davidmarino.model.Line;
import davidmarino.model.Point;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Authors: David Allen Stephan Marino
 * Date: 6/15/25
 */

public class TestPointIntersect {
    @Test
    public void testPointIntersect() {

        Point p1 = new Point(0, 0);
        Point p2 = new Point(2, 2);

        Point p3 = new Point(0, 2);
        Point p4 = new Point(2, 0);
        Line l1 = new Line(p3, p4);

        Method intersect;
        try {
            intersect = GeometryUtils.class.getDeclaredMethod("intersect", Point.class, Point.class, Line.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Point res;
        try {
            res = (Point) intersect.invoke(GeometryUtils.class, p1, p2, l1);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        boolean correctX = res.x == 1.0;
        boolean correctY = res.y == 1.0;
        Assert.assertTrue(correctX && correctY);
    }
}
