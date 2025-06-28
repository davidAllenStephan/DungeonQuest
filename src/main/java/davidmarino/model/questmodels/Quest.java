package davidmarino.model.questmodels;

import davidmarino.model.dungeonmodels.Dungeon;
import davidmarino.model.dungeonmodels.Zone;
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

    public Quest(Dungeon dungeon) {
        questLogs = new ArrayList<>();
        QuestLogCollection questLogCollection = new QuestLogCollection();
        Random random = new Random();
        for (Zone zone : dungeon.zones) {
            QuestLogList questLogList = questLogCollection.questLogCollectionMap.get(zone.zoneType);
            questLogs.add(questLogList.questLogs.get(random.nextInt(0, questLogList.questLogs.size())));
        }
    }

}
