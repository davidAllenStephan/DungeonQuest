package davidmarino.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.GLOBAL;
import davidmarino.model.dungeonmodels.Dungeon;
import davidmarino.model.questmodels.Quest;
import davidmarino.service.DungeonQuestService;
import davidmarino.service.dungeonservice.DungeonService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class DungeonQuest {
    @Autowired
    private DungeonService dungeonService;
    @Autowired
    private DungeonQuestService dungeonQuestService;

    @JsonProperty("dungeon")
    public Dungeon dungeon;
    @JsonProperty("quest")
    public Quest quest;

    public DungeonQuest(Parameters parameters) {
        dungeonService = new DungeonService(parameters);
        dungeonService = new DungeonQuestService(parameters);
    }


    public DungeonQuest(DungeonService dungeonService, DungeonQuestService dungeonQuestService) {
        this.dungeonService = dungeonService;
        this.dungeonQuestService = dungeonQuestService;
    }

    public void setDungeon() {
        dungeon = new Dungeon();
    }

    public void setQuest() {
        quest = new Quest();
    }

    public DungeonQuest(DungeonService dungeonService, Quest quest) {
        this.dungeonService = dungeonService;
        this.quest = quest;
    }

}
