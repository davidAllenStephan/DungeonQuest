package davidmarino.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import davidmarino.model.dungeonmodels.Dungeon;
import davidmarino.model.mapmodels.Map;
import davidmarino.model.questmodels.Quest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DungeonQuest {
    @JsonProperty("map")
    public Map map;
    @JsonProperty("dungeon")
    public Dungeon dungeon;
    @JsonProperty("quest")
    public Quest quest;

    public DungeonQuest() {

    }

    public DungeonQuest(Map map, Dungeon dungeon, Quest quest) {
        this.map = map;
        this.dungeon = dungeon;
        this.quest = quest;
    }

    public DungeonQuest(Parameters parameters) {
        map = new Map(parameters);
        dungeon = new Dungeon(parameters);
        quest = new Quest(dungeon);
    }

    public static Map getMap(Parameters parameters) {
        return new Map(parameters);
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
