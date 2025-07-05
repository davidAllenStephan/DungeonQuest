package davidmarino.quest.questmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.Parameters;
import davidmarino.dungeon.dungeonmodels.Dungeon;
import davidmarino.dungeon.dungeonmodels.DungeonCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class QuestCollection {
    @JsonProperty("quests")
    public List<Quest> quests;

    public QuestCollection(List<Quest> quests) {
        this.quests = quests;
    }

    public QuestCollection(DungeonCollection dungeonCollection, Parameters parameters) {
        // Select the combinations of monsters, characters, and artifacts.
        // Then pass the collections into Quest to fill the madlibs.

        quests = new ArrayList<>();
        for (Dungeon dungeon : dungeonCollection.dungeons) {
            quests.add(new Quest(dungeon, parameters));
        }
    }
}
