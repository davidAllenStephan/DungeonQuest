package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@DynamoDBTable(tableName = "DungeonQuestMonsters")
@Data
public class MonsterCategory {
    @DynamoDBHashKey(attributeName = "id")
    private final String id;
    @DynamoDBAttribute(attributeName = "monsterCategoryTitle")
    private final String monsterCategoryTitle;
    @DynamoDBAttribute(attributeName = "monsters")
    private final ArrayList<Monster> monsters;

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

}
