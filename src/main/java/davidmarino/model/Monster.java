package davidmarino.model;

import lombok.Data;

@Data
public class Monster {
    private String name;

    public Monster(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
