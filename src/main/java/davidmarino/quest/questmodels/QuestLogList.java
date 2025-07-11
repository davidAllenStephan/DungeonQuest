package davidmarino.quest.questmodels;

import davidmarino.dungeon.dungeonmodels.RoomType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class QuestLogList {
    public ArrayList<QuestLog> questLogs;
    public QuestLogList() {
        questLogs = new ArrayList<>();
    }
    public QuestLogList(ArrayList<String> questLogs, RoomType roomType) {
        this.questLogs = new ArrayList<>();
        for (String questLog : questLogs) {
            this.questLogs.add(new QuestLog(questLog, roomType));
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
