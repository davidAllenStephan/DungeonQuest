package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import davidmarino.DynamoDbConfig;
import davidmarino.webscraper.webscraperservice.DynamoDbUploadService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@DynamoDBTable(tableName = "DungeonQuestCharacters")
public class MajorCharacterCollection {
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "majorCharacters")
    private ArrayList<MajorCharacter> majorCharacters;

    public MajorCharacterCollection() {
        this.id = UUID.randomUUID().toString();
        this.majorCharacters = new ArrayList<>();
    }

    public MajorCharacterCollection(ArrayList<MajorCharacter> majorCharacters) {
        this.id = UUID.randomUUID().toString();
        this.majorCharacters = majorCharacters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<MajorCharacter> getMajorCharacters() {
        return majorCharacters;
    }

    public void setMajorCharacters(ArrayList<MajorCharacter> majorCharacters) {
        this.majorCharacters = majorCharacters;
    }

    public void uploadToDynamoDB() {
        DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
        DynamoDbUploadService dynamoDbUploadService = new DynamoDbUploadService(dynamoDbConfig.amazonDynamoDB());
        dynamoDbUploadService.uploadToDynamoDb(this);
    }

    @Override
    public String toString() {
        return majorCharacters.toString();
    }
}
