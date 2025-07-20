package davidmarino.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import davidmarino.Parameters;
import davidmarino.dungeon.dungeonmodels.Dungeon;
import davidmarino.quest.questservice.ArtifactCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class Router {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(Router.class);
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private final DungeonQuestController dungeonQuestController = new DungeonQuestController();
    @Autowired
    private Dungeon dungeon;

    public APIGatewayProxyResponseEvent route(APIGatewayProxyRequestEvent request, Context context) {
        if (request.getHttpMethod().equalsIgnoreCase("GET")) {
            if (request.getPath().contains("artifact-subcategories")) {
                return dungeonQuestController.getArtifactSubcategoryTitlesLambda();
            } else if (request.getPath().contains("major-characters")) {
                return dungeonQuestController.getMajorCharacterNames();
            } else if (request.getPath().contains("monster-categories")) {
                return dungeonQuestController.getMonsterCategoriesNames();
            }
        } else if (request.getHttpMethod().equalsIgnoreCase("POST")) {
            Parameters parameters = null;
            try {
                parameters = objectMapper.readValue(request.getBody(), Parameters.class);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
                return ControllerUtil.getLambdaStringResponse("Error while reading parameters", 500);
            }
            return dungeonQuestController.getLambdaDungeonQuest(parameters);
        }
        return ControllerUtil.getLambdaStringResponse("Invalid HTTP method", 500);
    }

}