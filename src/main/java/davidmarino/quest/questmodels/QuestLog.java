package davidmarino.quest.questmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.dungeon.dungeonmodels.RoomType;

public class QuestLog {
    @JsonProperty("zoneType")
    public RoomType roomType;
    @JsonProperty("questLog")
    public String questLog;

    public QuestLog(String questLog, RoomType roomType) {
        this.questLog = questLog;
        this.roomType = roomType;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QuestLog [zoneType=");
        builder.append(roomType);
        builder.append(", questLog=");
        builder.append(questLog);
        builder.append("]");
        return builder.toString();
    }
}
