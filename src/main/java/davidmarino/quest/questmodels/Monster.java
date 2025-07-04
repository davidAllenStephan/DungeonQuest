package davidmarino.quest.questmodels;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Monster {
    private final String id;
    private final String name;

    public Monster(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Monster(String name) {
        id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Monster() {
        id = UUID.randomUUID().toString();
        name = "";
    }

    @Override
    public String toString() {
        return name;
    }
}
