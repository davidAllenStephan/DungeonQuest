package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import davidmarino.DynamoDbConfig;
import davidmarino.webscraper.webscraperservice.DynamoDbUploadService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@DynamoDBTable(tableName = "DungeonQuestArtifacts")
public class ArtifactCollection {
    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "artifactCategories")
    private ArrayList<ArtifactCategory> artifactCategories;

    public ArtifactCollection() {
        id = UUID.randomUUID().toString();
        artifactCategories = new ArrayList<>();
    }

    public ArtifactCollection(ArrayList<ArtifactCategory> artifactCategories) {
        id = UUID.randomUUID().toString();
        this.artifactCategories = artifactCategories;
    }

    public void uploadToDynamoDB() {
        DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
        DynamoDbUploadService dynamoDbUploadService = new DynamoDbUploadService(dynamoDbConfig.amazonDynamoDB());
        dynamoDbUploadService.uploadToDynamoDb(this);
    }

    @Override
    public String toString() {
        return artifactCategories.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ArtifactCategory> getArtifactCategories() {
        return artifactCategories;
    }

    public void setArtifactCategories(ArrayList<ArtifactCategory> artifactCategories) {
        this.artifactCategories = artifactCategories;
    }
}
