package davidmarino.controller;

import davidmarino.model.Room;

public class RoomController {

    public static double getDistance(Room room1, Room room2) {
        /**
         * There are several orientations of the rooms.
         * Since to determine the intersection point between the rooms
         * I need the difference between the center of the room and the
         * border that faces the midpoint between the room being compared.
         *
         */
        double a = room1.center.x * room1.center.y;
        if (a < 0) {
            if (room1.center.x < 0) {
                double hyp = Math.sqrt(Math.pow(room1.center.x - room2.center.x, 2) + Math.pow(room1.center.y - room2.center.y, 2));
                double opp = room2.center.y - room1.center.y;
                double theta = Math.asin(opp / hyp);
                double midX = room1.center.x + room2.center.x;
                double midY = room1.center.y + room2.center.y;
                double sharedX = midX;
                double sharedY = room1.center.y;
                double sharedDy = midY - sharedY;
                double sharedDx = sharedX - room1.center.x;
                double borderDx = room1.xRadius - room1.center.x;
                double room1m = borderDx / sharedDx;
                double borderIntersectionY = sharedDy * room1m;
            }
        } else if (a >= 0) {
            if (room1.center.x >= 0) {
                double hyp = Math.sqrt(Math.pow(room1.center.x - room2.center.x, 2) + Math.pow(room1.center.y - room2.center.y, 2));
                double opp = room2.center.y - room1.center.y;
                double theta = Math.asin(opp / hyp);
                double midX = room1.center.x + room2.center.x;
                double midY = room1.center.y + room2.center.y;
                double sharedX = midX;
                double sharedY = room1.center.y;
                double sharedDy = midY - sharedY;
                double sharedDx = sharedX - room1.center.x;
                double borderDx = room1.xRadius - room1.center.x;
                double room1m = borderDx / sharedDx;
                double borderIntersectionY = sharedDy * room1m;
            }
        }
        return a;
    }
}
