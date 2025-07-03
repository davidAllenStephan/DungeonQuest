package davidmarino.quest.questmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.dungeon.dungeonmodels.ZoneType;

public class QuestLog {
    @JsonProperty("zoneType")
    public ZoneType zoneType;
    @JsonProperty("questLog")
    public String questLog;

    public QuestLog(String questLog, ZoneType zoneType) {
        this.questLog = questLog;
        this.zoneType = zoneType;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QuestLog [zoneType=");
        builder.append(zoneType);
        builder.append(", questLog=");
        builder.append(questLog);
        builder.append("]");
        return builder.toString();
    }
}
