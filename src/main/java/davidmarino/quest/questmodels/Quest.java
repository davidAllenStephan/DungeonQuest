package davidmarino.quest.questmodels;

import davidmarino.Parameters;
import davidmarino.dungeon.dungeonmodels.Dungeon;
import davidmarino.dungeon.dungeonmodels.Zone;
import davidmarino.quest.questservice.ArtifactCollectionService;
import davidmarino.quest.questservice.MajorCharacterCollectionService;
import davidmarino.quest.questservice.MonsterCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Service
public class Quest {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(Quest.class);
    private ArrayList<QuestLog> questLogs;


    public Quest() {
        questLogs = new ArrayList<>();
    }

    public Quest(Dungeon dungeon, Parameters parameters) {
        questLogs = new ArrayList<>();

        ArrayList<String> majorCharacters = new ArrayList<>(Arrays.asList(parameters.majorCharacters));
        ArrayList<String> artifactCategories = new ArrayList<>(Arrays.asList(parameters.artifactCategories));
        ArrayList<String> monsterCategories = new ArrayList<>(Arrays.asList(parameters.monsterCategories));

        MajorCharacterCollection majorCharacterCollection = MajorCharacterCollectionService.getFromDynamoDb("186274b2-2d1c-4ded-88c4-e4e64ccce99f", majorCharacters);
        ArtifactCollection artifactCollection = ArtifactCollectionService.getFromDynamoDb("145e864c-1120-4cb7-84f6-154b9351bf3f", artifactCategories);
        MonsterCollection monsterCollection = MonsterCollectionService.getFromDynamoDb("e23ab53f-d579-4485-9e2e-1db0293f659a", monsterCategories);

        QuestLogCollection questLogCollection = new QuestLogCollection(majorCharacterCollection, artifactCollection, monsterCollection);
        Random random = new Random();
        for (Zone zone : dungeon.zones) {
            QuestLogList questLogList = questLogCollection.questLogCollectionMap.get(zone.zoneType);
            questLogs.add(questLogList.questLogs.get(random.nextInt(0, questLogList.questLogs.size())));
        }
    }

}
