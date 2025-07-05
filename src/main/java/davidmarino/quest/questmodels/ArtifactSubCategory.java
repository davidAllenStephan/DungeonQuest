package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@DynamoDBTable(tableName = "DungeonQuestArtifacts")
public class ArtifactSubCategory {
    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "subCategoryTitle")
    private String subCategoryTitle;

    @DynamoDBAttribute(attributeName = "artifacts")
    private ArrayList<Artifact> artifacts;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubCategoryTitle() {
        return subCategoryTitle;
    }

    public void setSubCategoryTitle(String subCategoryTitle) {
        this.subCategoryTitle = subCategoryTitle;
    }

    public ArrayList<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(ArrayList<Artifact> artifacts) {
        this.artifacts = artifacts;
    }
}
