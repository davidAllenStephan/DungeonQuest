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
public class ArtifactCategory {
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "artifactCategoryTitle")
    private String artifactCategoryTitle;
    @DynamoDBAttribute(attributeName = "artifactSubCategories")
    private ArrayList<ArtifactSubCategory> artifactSubCategories;

    public ArtifactCategory() {
        id = UUID.randomUUID().toString();
        artifactCategoryTitle = "";
        artifactSubCategories = new ArrayList<>();
    }

    public ArtifactCategory(String artifactCategoryTitle) {
        id = UUID.randomUUID().toString();
        this.artifactCategoryTitle = artifactCategoryTitle;
        artifactSubCategories = new ArrayList<>();
    }

    public ArtifactCategory(String artifactCategoryTitle, ArrayList<ArtifactSubCategory> artifactSubCategories) {
        id = UUID.randomUUID().toString();
        this.artifactCategoryTitle = artifactCategoryTitle;
        this.artifactSubCategories = artifactSubCategories;
    }

    @Override
    public String toString() {
        return "\n[name=" + artifactCategoryTitle + ", artifactSubCategories=" + artifactSubCategories + "]";
    }
}
