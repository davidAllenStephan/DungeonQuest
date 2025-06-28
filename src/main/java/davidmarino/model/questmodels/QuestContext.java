package davidmarino.model.questmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestContext {
    @JsonProperty("entity")
    public String entity;
    @JsonProperty("monster")
    public String monster;
    @JsonProperty("trap")
    public String trap;

    public QuestContext(String entity, String monster, String trap) {
        this.entity = entity;
        this.monster = monster;
        this.trap = trap;
    }
}
