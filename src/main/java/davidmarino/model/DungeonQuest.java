package davidmarino.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import davidmarino.model.dungeonmodels.Dungeon;
import davidmarino.model.questmodels.Quest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DungeonQuest {
    @JsonProperty("dungeon")
    public Dungeon dungeon;
    @JsonProperty("quest")
    public Quest quest;

    public DungeonQuest() {

    }

    public DungeonQuest(Dungeon dungeon, Quest quest) {
        this.dungeon = dungeon;
        this.quest = quest;
    }

    public DungeonQuest(Parameters parameters) {
        dungeon = new Dungeon(parameters);
        quest = new Quest(dungeon);
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
