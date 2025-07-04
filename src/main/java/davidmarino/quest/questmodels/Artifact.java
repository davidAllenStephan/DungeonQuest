package davidmarino.quest.questmodels;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class Artifact {
    public String id;
    public String name;
    public String description;

    public Artifact(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }
    public Artifact() {

    }

    @Override
    public String toString() {
        return "Artifact [name=" + name + ", description=" + description + "]";
    }
}
