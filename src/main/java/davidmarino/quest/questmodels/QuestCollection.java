package davidmarino.quest.questmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.dungeon.dungeonmodels.Dungeon;
import davidmarino.dungeon.dungeonmodels.DungeonCollection;

import java.util.ArrayList;
import java.util.List;

public class QuestCollection {
    @JsonProperty("quests")
    public List<Quest> quests;

    public QuestCollection(List<Quest> quests) {
        this.quests = quests;
    }

    public QuestCollection(DungeonCollection dungeonCollection) {
        quests = new ArrayList<>();
        for (Dungeon dungeon : dungeonCollection.dungeons) {
            quests.add(new Quest(dungeon));
        }
    }
}
