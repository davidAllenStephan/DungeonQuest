package davidmarino.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import davidmarino.GLOBAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class Router {

    private static final Logger logger = LoggerFactory.getLogger(Router.class);
    private final DungeonQuestController dungeonQuestController = new DungeonQuestController();
    private final DungeonController dungeonController = new DungeonController();
    private final MapController mapController = new MapController();
    private final QuestController questController = new QuestController();

    public APIGatewayProxyResponseEvent route(APIGatewayProxyRequestEvent request, Context context) {
        String path = request.getPath();
        logger.info("Routing path: {}", path);

        boolean makeMap = Boolean.parseBoolean(request.getQueryStringParameters().get("makeMap"));
        boolean makeDungeon = Boolean.parseBoolean(request.getQueryStringParameters().get("makeDungeon"));
        boolean makeQuest = Boolean.parseBoolean(request.getQueryStringParameters().get("makeQuest"));

        GLOBAL.makeMap = makeMap;
        GLOBAL.makeDungeon = makeDungeon;
        GLOBAL.makeQuest= makeQuest;

//        try {
//            return switch (path) {
//                case "/" -> dungeonQuestController.handlePost(request, context);
//                case "/dungeon" -> dungeonController.handleDungeonPost(request, context);
//                case "/map" -> mapController.handleMapPost(request, context);
//                case "/quest" -> questController.handleQuestPost(request, context);
//                default -> ControllerUtil.getLambdaStringResponse("404 - Not Found", 404);
//            };
//        } catch (Exception e) {
//            logger.error("Routing error: ", e);
//            return ControllerUtil.getLambdaStringResponse("Internal Server Error", 500);
//        }

        return ControllerUtil.getLambdaStringResponse("Internal Server Error", 500);
    }

}