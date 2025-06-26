package davidmarino.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import davidmarino.model.questmodels.Quest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class QuestController {

    private final static Logger logger = LoggerFactory.getLogger(QuestController.class);

    @PostMapping("/quest")
    public ResponseEntity quest() {
        String questJson = Quest.exportMonsterCollectionToJson();
        return ControllerUtil.getStringResponse(questJson, 200);
    }

    public APIGatewayProxyResponseEvent handleQuestPost(APIGatewayProxyRequestEvent input, Context context) {
        try {
            String questJson = Quest.exportMonsterCollectionToJson();
            return ControllerUtil.getLambdaStringResponse(questJson, 200);
        } catch (Exception e) {
            logger.error(e.toString());
            return ControllerUtil.getLambdaStringResponse(e.toString(), 400);
        }
    }

}
