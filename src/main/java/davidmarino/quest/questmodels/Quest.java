package davidmarino.quest.questmodels;

import davidmarino.dungeon.dungeonmodels.Dungeon;
import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class Quest {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(Quest.class);
    private ArrayList<QuestLog> questLogs;


    public Quest() {
        questLogs = new ArrayList<>();
    }

    public Quest(Dungeon dungeon, QuestLogCollection questLogCollection) {
        questLogs = new ArrayList<>();
        Random random = new Random();
        for (DungeonType dungeonType : dungeon.roomTypes) {
            QuestLogList questLogList = questLogCollection.questLogCollectionMap.get(dungeonType);
            if (questLogList != null) {
                questLogs.add(questLogList.questLogs.get(random.nextInt(0, questLogList.questLogs.size())));
            }
        }
    }

    @Override
    public String toString() {
        return questLogs.toString();
    }

}
