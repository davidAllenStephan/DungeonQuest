package davidmarino.quest.questmodels;

import lombok.Data;

import java.util.UUID;

@Data
public class Monster {
    public String id;
    private String name;

    public Monster(String name) {
        id = UUID.randomUUID().toString();
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
