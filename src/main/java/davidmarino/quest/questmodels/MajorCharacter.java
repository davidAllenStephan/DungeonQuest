package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@DynamoDBTable(tableName = "DungeonQuestCharacters")
public class MajorCharacter {

    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "gender")
    private String gender;
    @DynamoDBAttribute(attributeName = "actor")
    private String actor;
    @DynamoDBAttribute(attributeName = "film")
    private String film;
    @DynamoDBAttribute(attributeName = "year")
    private String year;
    @DynamoDBAttribute(attributeName = "notes")
    private String notes;
    @DynamoDBAttribute(attributeName = "type")
    private String type;

    public MajorCharacter(String name, String gender, String actor, String film, String year, String notes, String type) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.gender = gender;
        this.actor = actor;
        this.film = film;
        this.year = year;
        this.notes = notes;
        this.type = type;
    }

    public MajorCharacter() {
        this.id = UUID.randomUUID().toString();
        this.name = "";
        this.gender = "";
        this.actor = "";
        this.film = "";
        this.year = "";
        this.notes = "";
        this.type = "";
    }

}