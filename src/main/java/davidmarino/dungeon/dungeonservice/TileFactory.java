package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.Tile;
import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import davidmarino.dungeon.dungeonviews.TileMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TileFactory {

    private final TileMap tileMap;

    @Autowired
    public TileFactory(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public Tile createTile(int x, int y, DungeonType dungeonType) {
        return new Tile(x, y, dungeonType, tileMap);
    }

}
