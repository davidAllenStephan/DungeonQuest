package davidmarino.quest.questmodels;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@DynamoDBTable(tableName = "DungeonQuestCharacters")
@Component
public class MajorCharacter {

    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "name-index", attributeName = "name")
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}