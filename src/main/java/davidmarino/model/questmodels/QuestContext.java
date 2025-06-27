package davidmarino.model.questmodels;

public class QuestContext {
    public String entity;
    public String monster;
    public String trap;

    public QuestContext(String entity, String monster, String trap) {
        this.entity = entity;
        this.monster = monster;
        this.trap = trap;
    }
}
