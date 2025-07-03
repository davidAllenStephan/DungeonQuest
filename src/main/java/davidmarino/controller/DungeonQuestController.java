package davidmarino.controller;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import davidmarino.model.DungeonQuest;
import davidmarino.model.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class DungeonQuestController {

    @PostMapping("/")
    public ResponseEntity dungeonQuest(@RequestBody Parameters parameters) {
        DungeonQuest dungeonQuest = new DungeonQuest(parameters);
        return ControllerUtil.getStringResponse(dungeonQuest.getJson(), 200);
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
