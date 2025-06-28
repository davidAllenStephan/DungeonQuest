package davidmarino.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import davidmarino.service.dungeonservice.DungeonService;
import davidmarino.model.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class DungeonController {

    private final static Logger logger = LoggerFactory.getLogger(DungeonController.class);

    @PostMapping("/dungeon")
    public ResponseEntity dungeon(@RequestBody Parameters parameters) {
        DungeonService dungeonService = new DungeonService(parameters);
        byte[] imageBytes = dungeonService.runDungeon();
        return ControllerUtil.getPngResponse(imageBytes);
    }

    public APIGatewayProxyResponseEvent handleDungeonPost(APIGatewayProxyRequestEvent input, Context context) {
        try {
            String base64Image = DungeonService.getBase64(input.getBody(), true);
            return ControllerUtil.getLambdaPngResponse(base64Image);
        } catch (Exception e) {
            logger.error(e.toString());
            return ControllerUtil.getLambdaStringResponse(e.toString(), 400);
        }
    }

}
