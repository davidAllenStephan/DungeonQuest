package davidmarino.dungeon.dungeonmodels;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TileCanvas {
    public ArrayList<ArrayList<Tile>> tileCanvas = new ArrayList<>();
    public int width;
    public int height;
    public Set<Tile> minSpanTree = new HashSet<>();

    public TileCanvas() {
        tileCanvas = new ArrayList<>();
        width = tileCanvas.size();
        height = tileCanvas.get(0).size();
    }

    public TileCanvas(ArrayList<ArrayList<Tile>> tileCanvas) {
        this.tileCanvas = tileCanvas;
        width = tileCanvas.size();
        height = tileCanvas.get(0).size();
    }

    public TileCanvas(Tile[][] tiles) {
        ArrayList<ArrayList<Tile>> tileCanvas = new ArrayList<>();
        for (Tile[] tile : tiles) {
            ArrayList<Tile> tileList = new ArrayList<>(Arrays.asList(tile));
            tileCanvas.add(tileList);
        }
        this.tileCanvas = tileCanvas;
        width = tileCanvas.size();
        height = tileCanvas.get(0).size();
    }

    public void primeMinSpanTree() {
        List<Tile> sites = new ArrayList<>();
        for (ArrayList<Tile> row : tileCanvas) {
            for (Tile tile : row) {
                if (tile.zoneType == ZoneType.SITE) {
                    sites.add(tile);
                }
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

    private double distance(Tile a, Tile b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    private void drawPath(Tile a, Tile b) {
        int x = a.x;
        int y = a.y;
        while (x != b.x) {
            find(x, y).zoneType = ZoneType.PATH;
            x += (b.x > x) ? 1 : -1;
        }
        while (y != b.y) {
            find(x, y).zoneType = ZoneType.PATH;
            y += (b.y > y) ? 1 : -1;
        }
        find(x, y).zoneType = ZoneType.PATH;
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

    public Tile find(int x, int y) {
        return tileCanvas.get(x).get(y);
    }

    public void toZone(Tile tileSite, int width, int height, ZoneType zoneType) {
        int startX = tileSite.x - width / 2;
        int endX = tileSite.x + width / 2;
        int startY = tileSite.y - height / 2;
        int endY = tileSite.y + height / 2;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                this.find(x, y).zoneType = zoneType;
            }
        }
    }
}
