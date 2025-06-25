package davidmarino.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MonsterCollection {
    private ArrayList<MonsterCategory> monsterCategories;

    public MonsterCollection() {
       this.monsterCategories = new ArrayList<>();
    }

    public void addCategory(MonsterCategory monsterCategory) {
        monsterCategories.add(monsterCategory);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (MonsterCategory monsterCategory : monsterCategories) {
            stringBuilder.append(monsterCategory.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
