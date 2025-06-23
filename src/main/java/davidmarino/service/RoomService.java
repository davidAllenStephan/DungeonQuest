package davidmarino.service;

import davidmarino.model.Edge;
import davidmarino.model.Line;
import davidmarino.model.Point;
import davidmarino.model.Room;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    public static Point findBoundIntersection(Room A, Room B) {
        Line intersection_AB = new Line(A.center, B.center);
        Line L_ap = new Line(A.center, new Point(A.center.x + A.xRadius, A.center.y + A.yRadius));
        Line L_an = new Line(A.center, new Point(A.center.x + A.xRadius, A.center.y - A.yRadius));
        boolean isBelowL_ap = LineService.isBelow(L_ap, B.center);
        boolean isBelowL_an = LineService.isBelow(L_an, B.center);
        if (isBelowL_ap && isBelowL_an) {
            Line edge = new Line(A.bounds.get(1), A.bounds.get(0));
            return LineService.intersect(edge, intersection_AB);
        } else if (isBelowL_ap && !isBelowL_an) {
            Line edge = new Line(A.bounds.get(1), A.bounds.get(2));
            return LineService.intersect(edge, intersection_AB);
        } else if (!isBelowL_ap && !isBelowL_an) {
            Line edge = new Line(A.bounds.get(2), A.bounds.get(3));
            return LineService.intersect(edge, intersection_AB);
        } else if (!isBelowL_ap && isBelowL_an) {
            Line edge = new Line(A.bounds.get(0), A.bounds.get(3));
            return LineService.intersect(edge, intersection_AB);
        }
        return null;
    }

    public static double getDistance(Room A, Room B) {
        Point boundIntersectionA = findBoundIntersection(A, B);
        Point boundIntersectionB = findBoundIntersection(B, A);
        return Math.sqrt(Math.pow(boundIntersectionB.x - boundIntersectionA.x, 2) + Math.pow(boundIntersectionB.y - boundIntersectionA.y, 2));
    }

    public static boolean inBounds(Room A, Room B) {
        double leftA = A.center.x - A.xRadius;
        double rightA = A.center.x + A.xRadius;
        double topA = A.center.y + A.yRadius;
        double bottomA = A.center.y - A.yRadius;

        double leftB = B.center.x - B.xRadius;
        double rightB = B.center.x + B.xRadius;
        double topB = B.center.y + B.yRadius;
        double bottomB = B.center.y - B.yRadius;

        return !(leftA >= rightB || rightA <= leftB || topA <= bottomB || bottomA >= topB);
    }

    public static boolean isValid(ArrayList<Room> rooms, Room room) {
        for (Room r : rooms) {
            if (inBounds(r, room)) {
                return false;
            }
        }
        return true;
    }

    public static Set<Edge> primMST(ArrayList<Room> rooms) {
        Set<Room> visited = new HashSet<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));
        Set<Edge> mst = new HashSet<>();
        Room start = rooms.get(0);
        visited.add(start);
        for (Map.Entry<Room, Double> entry : start.neighbors.entrySet()) {
            minHeap.add(new Edge(start, entry.getKey(), entry.getValue()));
        }
        while (!minHeap.isEmpty() && visited.size() < rooms.size()) {
            Edge edge = minHeap.poll();
            if (visited.contains(edge.to)) continue;
            visited.add(edge.to);
            mst.add(edge);
            for (Map.Entry<Room, Double> neighbor : edge.to.neighbors.entrySet()) {
                if (!visited.contains(neighbor.getKey())) {
                    minHeap.add(new Edge(edge.to, neighbor.getKey(), neighbor.getValue()));
                }
            }
        }
        return mst;
    }

    public static ArrayList<Room> generateRooms(int numberOfRooms, int width, int height, int minimumRoomWidth, int minimumRoomHeight, int maximumRoomWidth, int maximumRoomHeight) {
        ArrayList<Room> rooms = new ArrayList<>();
        for (int i = 0; i < numberOfRooms; i++) {
            if (rooms.isEmpty()) {
                rooms.add(new Room(width, height, minimumRoomWidth, minimumRoomHeight, maximumRoomWidth, maximumRoomHeight));
                continue;
            }
            while (true) {
                Room room = new Room(width, height, minimumRoomWidth, minimumRoomHeight, maximumRoomWidth, maximumRoomHeight);
                if (RoomService.isValid(rooms, room)) {
                    rooms.add(room);
                    break;
                }
            }
        }
        return rooms;
    }

}
