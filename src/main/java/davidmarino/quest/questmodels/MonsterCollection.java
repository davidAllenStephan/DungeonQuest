package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import davidmarino.DynamoDbConfig;
import davidmarino.webscraper.webscraperservice.DynamoDbUploadService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Data
@Component
@DynamoDBTable(tableName = "DungeonQuestMonsters")
public class MonsterCollection {
    @DynamoDBHashKey(attributeName = "id")
    private final String id;
    @DynamoDBAttribute(attributeName = "monsterCategories")
    private final ArrayList<MonsterCategory> monsterCategories;

    public MonsterCollection() {
        id = UUID.randomUUID().toString();
        monsterCategories = new ArrayList<>();
    }

    public void addCategory(MonsterCategory monsterCategory) {
        monsterCategories.add(monsterCategory);
    }

    public void uploadToDynamoDB() {
        DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
        DynamoDbUploadService dynamoDbUploadService = new DynamoDbUploadService(dynamoDbConfig.amazonDynamoDB());
        dynamoDbUploadService.uploadToDynamoDb(this);
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
