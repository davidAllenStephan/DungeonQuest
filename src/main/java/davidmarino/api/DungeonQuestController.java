package davidmarino.api;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import davidmarino.DungeonQuest;
import davidmarino.Parameters;
import davidmarino.quest.questservice.ArtifactCollectionService;
import davidmarino.quest.questservice.MajorCharacterCollectionService;
import davidmarino.quest.questservice.MonsterCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class DungeonQuestController {

    @PostMapping("/dungeonquest")
    public ResponseEntity dungeonQuest(@RequestBody Parameters parameters) {
        DungeonQuest dungeonQuest = new DungeonQuest(parameters);
        return ControllerUtil.getStringResponse(dungeonQuest.getJson(), 200);
    }

    @GetMapping("/artifact-subcategories")
    public ResponseEntity<ArrayList<String>> getAllSubCategoryTitles() {
        ArrayList<String> titles = ArtifactCollectionService.getAllSubCategoryTitles();
        return ResponseEntity.ok(titles);
    }

    @GetMapping("/major-characters")
    public ResponseEntity<ArrayList<String>> getAllMajorCharacters() {
        ArrayList<String> titles = MajorCharacterCollectionService.getAllMajorCharacterNames();
        return ResponseEntity.ok(titles);
    }

    @GetMapping("/monster-categories")
    public ResponseEntity<ArrayList<String>> getAllMonsterCategories() {
        ArrayList<String> titles = MonsterCollectionService.getAllMonsterCategories();
        return ResponseEntity.ok(titles);
    }

    public APIGatewayProxyResponseEvent getMonsterCategoriesNames() {
        ArrayList<String> monsterCategoryNames = MonsterCollectionService.getAllMonsterCategories();
        try {
            String json = new ObjectMapper().writeValueAsString(monsterCategoryNames);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(getDefaultHeaders())
                    .withBody(json);
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withHeaders(getDefaultHeaders())
                    .withBody("{\"error\":\"Failed\"}");
        }
    }

    public APIGatewayProxyResponseEvent getMajorCharacterNames() {
        ArrayList<String> majorCharacterNames = MajorCharacterCollectionService.getAllMajorCharacterNames();
        try {
            String json = new ObjectMapper().writeValueAsString(majorCharacterNames);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(getDefaultHeaders())
                    .withBody(json);
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withHeaders(getDefaultHeaders())
                    .withBody("{\"error\":\"Failed\"}");
        }
    }

    public APIGatewayProxyResponseEvent getArtifactSubcategoryTitlesLambda() {
        ArrayList<String> titles = ArtifactCollectionService.getAllSubCategoryTitles();
        try {
            String json = new ObjectMapper().writeValueAsString(titles);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(getDefaultHeaders())
                    .withBody(json);
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withHeaders(getDefaultHeaders())
                    .withBody("{\"error\":\"Failed to serialize subcategory titles\"}");
        }
    }

    public APIGatewayProxyResponseEvent getLambdaDungeonQuest(Parameters parameters) {
        DungeonQuest dungeonQuest = new DungeonQuest(parameters);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(getDefaultHeaders())
                .withBody(dungeonQuest.getJson());
    }

    private Map<String, String> getDefaultHeaders() {
        return Map.of(
                "Access-Control-Allow-Origin", "*",
                "Access-Control-Allow-Headers", "Content-Type",
                "Content-Type", "application/json"
        );
    }

}
