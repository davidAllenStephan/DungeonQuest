package davidmarino.controller;

import davidmarino.model.Line;
import davidmarino.model.Point;
import davidmarino.model.Room;

public class RoomController {

    public static Point findBoundIntersection(Room A, Room B) {
        Line intersection_AB = new Line(A.center, B.center);

        Line L_ap = new Line(A.center, new Point(A.center.x + A.xRadius, A.center.y + A.yRadius));
        Line L_an = new Line(A.center, new Point(A.center.x + A.xRadius, A.center.y - A.yRadius));
        boolean isBelowL_ap = LineController.isBelow(L_ap, B.center);
        boolean isBelowL_an = LineController.isBelow(L_an, B.center);

        if (isBelowL_ap && isBelowL_an) {
            Line edge = new Line(A.bounds.get(1), A.bounds.get(0));
            return LineController.intersect(edge, intersection_AB);
        } else if (isBelowL_ap && !isBelowL_an) {
            Line edge = new Line(A.bounds.get(1), A.bounds.get(2));
            return LineController.intersect(edge, intersection_AB);
        } else if (!isBelowL_ap && !isBelowL_an) {
            Line edge = new Line(A.bounds.get(2), A.bounds.get(3));
            return LineController.intersect(edge, intersection_AB);
        } else if (!isBelowL_ap && isBelowL_an) {
            Line edge = new Line(A.bounds.get(0), A.bounds.get(3));
            return LineController.intersect(edge, intersection_AB);
        }
        return null;
    }

    public static double getDistance(Room A, Room B) {
        System.out.println(A);
        System.out.println(B);

        Point boundIntersectionA = findBoundIntersection(A, B);
        Point boundIntersectionB = findBoundIntersection(B, A);

        System.out.println("boundIntersectionA: " + boundIntersectionA);
        System.out.println("boundIntersectionB: " + boundIntersectionB);

        return Math.sqrt(Math.pow(boundIntersectionB.x - boundIntersectionA.x, 2) + Math.pow(boundIntersectionB.y - boundIntersectionA.y, 2));
    }
}
