package davidmarino.quest.questmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.dungeon.dungeonmodels.enums.DungeonType;

public class QuestLog {
    @JsonProperty("zoneType")
    public DungeonType dungeonType;
    @JsonProperty("questLog")
    public String questLog;

    public QuestLog(String questLog, DungeonType dungeonType) {
        this.questLog = questLog;
        this.dungeonType = dungeonType;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QuestLog [zoneType=");
        builder.append(dungeonType);
        builder.append(", questLog=");
        builder.append(questLog);
        builder.append("]");
        return builder.toString();
    }
}
