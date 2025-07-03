package davidmarino;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import davidmarino.dungeon.dungeonmodels.DungeonCollection;
import davidmarino.map.mapmodels.Map;
import davidmarino.quest.questmodels.QuestCollection;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DungeonQuest {
    @JsonProperty("map")
    public Map map;
    @JsonProperty("dungeons")
    public DungeonCollection dungeons;
    @JsonProperty("quests")
    public QuestCollection quests;

    public DungeonQuest() {

    }

    public DungeonQuest(Map map, DungeonCollection dungeons, QuestCollection quests) {
        this.map = map;
        this.dungeons = dungeons;
        this.quests = quests;
    }

    public DungeonQuest(Parameters parameters) {
        map = new Map(parameters);
        dungeons = new DungeonCollection(parameters);
        quests = new QuestCollection(dungeons);
    }

    public static Map getMap(Parameters parameters) {
        return new Map(parameters);
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
