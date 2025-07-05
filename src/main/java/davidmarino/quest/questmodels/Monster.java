package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@DynamoDBTable(tableName = "DungeonQuestMonsters")
@Component
public class Monster {
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "name")
    private String name;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
