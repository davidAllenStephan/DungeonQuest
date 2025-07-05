package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@DynamoDBTable(tableName = "DungeonQuestMonsters")
public class MonsterCategory {
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "monsterCategoryTitle-index", attributeName = "monsterCategoryTitle")
    private String monsterCategoryTitle;
    @DynamoDBAttribute(attributeName = "monsters")
    private ArrayList<Monster> monsters;

    public MonsterCategory() {
        id = UUID.randomUUID().toString();
        monsterCategoryTitle = "";
        monsters = new ArrayList<>();
    }

    public MonsterCategory(String monsterCategoryTitle) {
        id = UUID.randomUUID().toString();
        this.monsterCategoryTitle = monsterCategoryTitle;
        monsters = new ArrayList<>();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonsterCategoryTitle() {
        return monsterCategoryTitle;
    }

    public void setMonsterCategoryTitle(String monsterCategoryTitle) {
        this.monsterCategoryTitle = monsterCategoryTitle;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }
}
