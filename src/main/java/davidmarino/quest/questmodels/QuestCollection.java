package davidmarino.quest.questmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.Parameters;
import davidmarino.dungeon.dungeonmodels.Dungeon;
import davidmarino.dungeon.dungeonmodels.DungeonCollection;
import davidmarino.quest.questservice.ArtifactCollectionService;
import davidmarino.quest.questservice.MajorCharacterCollectionService;
import davidmarino.quest.questservice.MonsterCollectionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class QuestCollection {
    @JsonProperty("quests")
    public List<Quest> quests;

    public QuestCollection() {}

    public QuestCollection(DungeonCollection dungeonCollection, Parameters parameters) {
        quests = new ArrayList<>();
        ArrayList<String> majorCharacters = new ArrayList<>(Arrays.asList(parameters.majorCharacters));
        ArrayList<String> artifactCategories = new ArrayList<>(Arrays.asList(parameters.artifactCategories));
        ArrayList<String> monsterCategories = new ArrayList<>(Arrays.asList(parameters.monsterCategories));

        MajorCharacterCollection majorCharacterCollection = MajorCharacterCollectionService.getFromDynamoDb("186274b2-2d1c-4ded-88c4-e4e64ccce99f", majorCharacters);
        ArtifactCollection artifactCollection = ArtifactCollectionService.getFromDynamoDb("145e864c-1120-4cb7-84f6-154b9351bf3f", artifactCategories);
        MonsterCollection monsterCollection = MonsterCollectionService.getFromDynamoDb("e23ab53f-d579-4485-9e2e-1db0293f659a", monsterCategories);

        for (Dungeon dungeon : dungeonCollection.dungeons) {
            quests.add(new Quest(dungeon, majorCharacterCollection, artifactCollection, monsterCollection));
        }
    }
}
