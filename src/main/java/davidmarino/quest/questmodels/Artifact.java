package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@DynamoDBTable(tableName = "DungeonQuestArtifacts")
@Data
public class Artifact {
    @DynamoDBHashKey(attributeName = "id")
    private final String id;
    @DynamoDBAttribute(attributeName = "name")
    private final String name;
    @DynamoDBAttribute(attributeName = "description")
    private final String description;

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

    @Override
    public String toString() {
        return "Artifact [name=" + name + ", description=" + description + "]";
    }
}
