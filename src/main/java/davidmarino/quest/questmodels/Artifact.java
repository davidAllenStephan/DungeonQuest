package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@DynamoDBTable(tableName = "DungeonQuestArtifacts")
public class Artifact {
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "description")
    private String description;

    public Artifact(String name, String description) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

    public Artifact() {
        id = UUID.randomUUID().toString();
        name = "";
        description = "";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Artifact [name=" + name + ", description=" + description + "]";
    }
}
