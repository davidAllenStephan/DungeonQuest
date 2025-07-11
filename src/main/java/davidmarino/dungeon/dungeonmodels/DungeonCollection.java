package davidmarino.dungeon.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.Parameters;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DungeonCollection {
    @JsonProperty("dungeons")
    public List<Dungeon> dungeons;

    public DungeonCollection() {}

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
