package davidmarino.dungeon.dungeonmodels;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TileCanvas {
    private Tile[] tiles;
    public int width;
    public int height;

    public TileCanvas() {

    }

    public TileCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width * height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[index(x, y)] = new Tile(x, y);
            }
        }
    }


    public void drawPath(Tile from, Tile to) {
        int x1 = from.x;
        int y1 = from.y;
        int x2 = to.x;
        int y2 = to.y;

        Random rand = new Random();
        boolean horizontalFirst = rand.nextBoolean();

        if (horizontalFirst) {
            for (int x = x1; x != x2; x += Integer.compare(x2, x1)) {
                find(x, y1).roomBuilderType = RoomBuilderType.CORRIDOR;
            }
            for (int y = y1; y != y2; y += Integer.compare(y2, y1)) {
                find(x2, y).roomBuilderType = RoomBuilderType.CORRIDOR;
            }
        } else {
            for (int y = y1; y != y2; y += Integer.compare(y2, y1)) {
                find(x1, y).roomBuilderType = RoomBuilderType.CORRIDOR;
            }
            for (int x = x1; x != x2; x += Integer.compare(x2, x1)) {
                find(x, y2).roomBuilderType = RoomBuilderType.CORRIDOR;
            }
        }
        find(x2, y2).roomBuilderType = RoomBuilderType.CORRIDOR;
    }

    private double distance(Tile a, Tile b) {
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public class TileDistance implements Comparable<TileDistance> {
        public final Tile from;
        public final Tile to;
        public final double distance;

        public TileDistance(Tile from, Tile to, double distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        @Override
        public int compareTo(TileDistance other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    public void primeMinSpanTree() {
        List<Tile> sites = new ArrayList<>();
        for (Tile tile : tiles) {
            if (tile.roomBuilderType == RoomBuilderType.SITE) {
                sites.add(tile);
            }
        }

        if (sites.size() < 2) return;

        Set<Tile> visited = new HashSet<>();
        PriorityQueue<TileDistance> minHeap = new PriorityQueue<>(Comparator.comparingDouble(td -> td.distance));
        Tile start = sites.get(0);
        visited.add(start);

        for (Tile other : sites) {
            if (!other.equals(start)) {
                minHeap.add(new TileDistance(start, other, distance(start, other)));
            }
        }

        while (!minHeap.isEmpty() && visited.size() < sites.size()) {
            TileDistance next = minHeap.poll();
            if (visited.contains(next.to)) continue;
            visited.add(next.to);
            drawPath(next.from, next.to);
            for (Tile other : sites) {
                if (!visited.contains(other)) {
                    minHeap.add(new TileDistance(next.to, other, distance(next.to, other)));
                }
            }
        }
    }

    private int index(int x, int y) {
        return y * width + x;
    }

    public Tile find(int x, int y) {
        return tiles[y * width + x];
    }

    public void setZone(int x, int y, RoomBuilderType roomBuilderType) {
        find(x,y).roomBuilderType = roomBuilderType;
    }

    public void toZone(Tile tileSite, int zoneWidth, int zoneHeight, RoomBuilderType roomBuilderType) {
        int halfWidth = zoneWidth / 2;
        int halfHeight = zoneHeight / 2;

        int startX = Math.max(0, tileSite.x - halfWidth);
        int endX = Math.min(width - 1, tileSite.x + zoneWidth - halfWidth - 1);
        int startY = Math.max(0, tileSite.y - halfHeight);
        int endY = Math.min(height - 1, tileSite.y + zoneHeight - halfHeight - 1);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                setZone(x, y, roomBuilderType);
            }
        }
    }

}