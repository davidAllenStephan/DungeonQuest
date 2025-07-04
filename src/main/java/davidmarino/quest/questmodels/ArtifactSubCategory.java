package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@DynamoDBTable(tableName = "DungeonQuestArtifacts")
@Data
public class ArtifactSubCategory {
    @DynamoDBHashKey(attributeName = "id")
    private final String id;

    @DynamoDBAttribute(attributeName = "subCategoryTitle")
    private final String subCategoryTitle;

    @DynamoDBAttribute(attributeName = "artifacts")
    private final ArrayList<Artifact> artifacts;

    public ArtifactSubCategory() {
        id = UUID.randomUUID().toString();
        subCategoryTitle = "";
        artifacts = new ArrayList<>();
    }

    public ArtifactSubCategory(String subCategoryTitle, ArrayList<Artifact> artifacts) {
        id = UUID.randomUUID().toString();
        this.subCategoryTitle = subCategoryTitle;
        this.artifacts = artifacts;
    }

    public ArtifactSubCategory(String subCategoryTitle) {
        id = UUID.randomUUID().toString();
        this.subCategoryTitle = subCategoryTitle;
        this.artifacts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "[subCategoryTitle=" + subCategoryTitle + ", artifacts=" + artifacts + "]";
    }
}
