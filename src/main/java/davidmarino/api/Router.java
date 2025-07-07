package davidmarino.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import davidmarino.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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

    public APIGatewayProxyResponseEvent route(APIGatewayProxyRequestEvent request, Context context) {
        Parameters parameters = null;
        try {
            parameters = objectMapper.readValue(request.getBody(), Parameters.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return ControllerUtil.getLambdaStringResponse("Error while reading parameters", 500);
        }
        return dungeonQuestController.getLambdaDungeonQuest(parameters);
    }

}