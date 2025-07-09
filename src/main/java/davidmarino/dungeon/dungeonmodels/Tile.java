package davidmarino.dungeon.dungeonmodels;

public class Tile {
    public int x;
    public int y;
    public ZoneType zoneType;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

/**
 * [x, [y]] Plane is first quadrant to avoid negatives
 */
