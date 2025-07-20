package davidmarino.dungeon.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.Parameters;
import davidmarino.dungeon.dungeonservice.TileFactory;
import davidmarino.dungeon.dungeonviews.TileMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DungeonCollection {
    @JsonProperty("dungeons")
    public List<Dungeon> dungeons;

    public DungeonCollection(Parameters parameters) {
        dungeons = new ArrayList<>();
        TileFactory tileFactory = new TileFactory(new TileMap());
        for (int i = 0; i < parameters.numberOfDungeons; i++) {
            dungeons.add(new Dungeon(parameters, tileFactory));
        }
    }

    @Override
    public String toString() {
        return dungeons.toString();
    }
}
