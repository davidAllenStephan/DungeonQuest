package davidmarino.dungeon.dungeonmodels;

import org.springframework.stereotype.Component;

import java.awt.Point;
import java.util.*;

@Component
public class TileCanvas {
    private final Map<Point, Tile> tiles = new HashMap<>();
    public int width;
    public int height;

    public TileCanvas() {}

    public TileCanvas(Tile[][] tileArray) {
        this.width = tileArray.length;
        this.height = tileArray[0].length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile t = tileArray[x][y];
                tiles.put(new Point(t.x, t.y), t);
            }
        }
    }

    public Tile find(int x, int y) {
        return tiles.getOrDefault(new Point(x, y), Tile.empty(x, y));
    }

    public void setTile(Tile tile) {
        tiles.put(new Point(tile.x, tile.y), tile);
    }

    public Collection<Tile> allTiles() {
        return tiles.values();
    }

    public void primeMinSpanTree() {
        List<Tile> sites = new ArrayList<>();
        for (Tile tile : tiles.values()) {
            if (tile.zoneType == ZoneType.SITE) {
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

    private void drawPath(Tile a, Tile b) {
        int x = a.x;
        int y = a.y;
        while (x != b.x) {
            setZone(x, y, ZoneType.PATH);
            x += (b.x > x) ? 1 : -1;
        }
        while (y != b.y) {
            setZone(x, y, ZoneType.PATH);
            y += (b.y > y) ? 1 : -1;
        }
        setZone(x, y, ZoneType.PATH);
    }

    private void setZone(int x, int y, ZoneType zoneType) {
        Tile tile = find(x, y);
        tile.zoneType = zoneType;
        setTile(tile);
    }

    private double distance(Tile a, Tile b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    private static class TileDistance {
        Tile from;
        Tile to;
        double distance;
        public TileDistance(Tile from, Tile to, double distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }
    }

    public void toZone(Tile tileSite, int zoneWidth, int zoneHeight, ZoneType zoneType) {
        int startX = tileSite.x - zoneWidth / 2;
        int endX = tileSite.x + zoneWidth / 2;
        int startY = tileSite.y - zoneHeight / 2;
        int endY = tileSite.y + zoneHeight / 2;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                setZone(x, y, zoneType);
            }
        }
    }
}