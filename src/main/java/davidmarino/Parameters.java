package davidmarino;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * Class {@code Parameters} holds the values that influence the model.
 */

@Component
@Data
public class Parameters {
    // MAP
    @JsonProperty("map_scale")
    public int mapScale = 5;

    @JsonProperty("map_smoothing_count")
    public int numberOfLloydIterations = 5;

    @JsonProperty("water_level")
    public double waterLevel = 0.4;

    @JsonProperty("coast_level")
    public double coastLevel = 0.45;

    @JsonProperty("white_cap_level")
    public double whiteCapLevel = 0.85;

    @JsonProperty("erosion_start_percent")
    public double startPercent = 0.5;

    @JsonProperty("erosion_spread_percent")
    public double spreadChance = 0.8;

    @JsonProperty("erosion_max_spread")
    public int maxChunks = 20;

    @JsonProperty("erosion_max_steps")
    public int maxStepsPerChunk = 20;

    // DUNGEON
    @JsonProperty("number_of_dungeons")
    public int numberOfDungeons;

    @JsonProperty("number_of_rooms")
    public int numberOfRooms;

    @JsonProperty("minimum_room_width")
    public int minimumRoomWidth;

    @JsonProperty("maximum_room_width")
    public int maximumRoomWidth;

    @JsonProperty("minimum_room_height")
    public int minimumRoomHeight;

    @JsonProperty("maximum_room_height")
    public int maximumRoomHeight;

    @JsonProperty("common_item_chance")
    public double commonItemChance;

    @JsonProperty("uncommon_item_chance")
    public double uncommonItemChance;

    @JsonProperty("rare_item_chance")
    public double rareItemChance;

    @JsonProperty("legendary_item_chance")
    public double legendaryItemChance;

    @JsonProperty("mystical_item_chance")
    public double mysticalItemChance;

    // QUEST
    @JsonProperty("artifact_categories")
    public String[] artifactCategories;

    @JsonProperty("monster_categories")
    public String[] monsterCategories;

    @JsonProperty("major_characters")
    public String[] majorCharacters;

    public Parameters(int mapScale,
                      int numberOfLloydIterations,
                      double waterLevel,
                      double coastLevel,
                      double whiteCapLevel,
                      double startPercent,
                      double spreadChance,
                      int maxChunks,
                      int maxStepsPerChunk,

                      int numberOfDungeons,
                      int numberOfRooms,
                      int minimumRoomWidth,
                      int maximumRoomWidth,
                      int minimumRoomHeight,
                      int maximumRoomHeight,
                      double commonItemChance,
                      double uncommonItemChance,
                      double rareItemChance,
                      double legendaryItemChance,
                      double mysticalItemChance,

                      String[] artifactCategories,
                      String[] monsterCategories,
                      String[] majorCharacters) {

        this.mapScale = mapScale;
        this.numberOfLloydIterations = numberOfLloydIterations;
        this.waterLevel = waterLevel;
        this.coastLevel = coastLevel;
        this.whiteCapLevel = whiteCapLevel;
        this.startPercent = startPercent;
        this.spreadChance = spreadChance;
        this.maxChunks = maxChunks;
        this.maxStepsPerChunk = maxStepsPerChunk;

        this.numberOfDungeons = numberOfDungeons;
        this.numberOfRooms = numberOfRooms;
        this.minimumRoomWidth = minimumRoomWidth;
        this.maximumRoomWidth = maximumRoomWidth;
        this.minimumRoomHeight = minimumRoomHeight;
        this.maximumRoomHeight = maximumRoomHeight;
        this.commonItemChance = commonItemChance;
        this.uncommonItemChance = uncommonItemChance;
        this.rareItemChance = rareItemChance;
        this.legendaryItemChance = legendaryItemChance;
        this.mysticalItemChance = mysticalItemChance;

        this.artifactCategories = artifactCategories;
        this.monsterCategories = monsterCategories;
        this.majorCharacters = majorCharacters;
    }
    public Parameters() {

    }
}
