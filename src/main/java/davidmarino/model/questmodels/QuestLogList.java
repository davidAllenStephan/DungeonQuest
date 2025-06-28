package davidmarino.model.questmodels;

import davidmarino.model.dungeonmodels.ZoneType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class QuestLogList {
    public ArrayList<QuestLog> questLogs;
    public QuestLogList() {
        questLogs = new ArrayList<>();
    }
    public QuestLogList(ArrayList<String> questLogs, ZoneType zoneType) {
        this.questLogs = new ArrayList<>();
        for (String questLog : questLogs) {
            this.questLogs.add(new QuestLog(questLog, zoneType));
        }
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (QuestLog questLog : questLogs) {
            stringBuilder.append(questLog + "\n");
        }
        return stringBuilder.toString();
    }
}
