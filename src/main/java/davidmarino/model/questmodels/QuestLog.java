package davidmarino.model.questmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.model.dungeonmodels.ZoneType;

public class QuestLog {
    @JsonProperty("zoneType")
    ZoneType zoneType;
    @JsonProperty("questLog")
    String questLog;

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
