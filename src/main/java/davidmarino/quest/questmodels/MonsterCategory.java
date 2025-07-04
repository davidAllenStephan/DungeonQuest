package davidmarino.quest.questmodels;

import lombok.Data;

import java.util.ArrayList;
import java.util.UUID;

@Data
public class MonsterCategory {
    public String id;
    private String monsterCategoryTitle;
    private ArrayList<Monster> monsters;

    public MonsterCategory(String monsterCategoryTitle) {
        id = UUID.randomUUID().toString();
        this.monsterCategoryTitle = monsterCategoryTitle;
        this.monsters = new ArrayList<>();
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MonsterCategory [name=");
        stringBuilder.append(monsterCategoryTitle);
        stringBuilder.append(", monsters=");
        stringBuilder.append(monsters);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}
