package davidmarino.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MonsterCategory {
    private String name;
    private ArrayList<Monster> monsters;

    public MonsterCategory(String name) {
        this.name = name;
        this.monsters = new ArrayList<>();
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MonsterCategory [name=");
        stringBuilder.append(name);
        stringBuilder.append(", monsters=");
        stringBuilder.append(monsters);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}
