package davidmarino.dungeon.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.model.Parameters;

import java.util.ArrayList;
import java.util.List;

public class DungeonCollection {
    @JsonProperty("dungeons")
    public List<Dungeon> dungeons;

    public DungeonCollection(List<Dungeon> dungeons) {
        this.dungeons = dungeons;
    }

    public DungeonCollection(Parameters parameters) {
        dungeons = new ArrayList<>();
        for (int i = 0; i < parameters.numberOfDungeons; i++) {
            dungeons.add(new Dungeon(parameters));
        }
    }
}
