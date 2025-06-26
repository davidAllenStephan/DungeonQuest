package davidmarino.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import davidmarino.model.MapDungeon;
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
public class MapController {

    private final static Logger logger = LoggerFactory.getLogger(DungeonController.class);

    @PostMapping("/map")
    public ResponseEntity map(@RequestBody Parameters parameters) {
        MapDungeon mapDungeon = new MapDungeon(parameters);
        byte[] imageBytes = mapDungeon.runMap();
        return ControllerUtil.getPngResponse(imageBytes);
    }

    public APIGatewayProxyResponseEvent handleMapPost(APIGatewayProxyRequestEvent input, Context context) {
        try {
            String base64Image = MapDungeon.getBase64(input.getBody(), false);
            return ControllerUtil.getLambdaPngResponse(base64Image);
        } catch (Exception e) {
            logger.error(e.toString());
            return ControllerUtil.getLambdaStringResponse(e.toString(), 400);
        }
    }
}
