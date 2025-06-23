package davidmarino.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import davidmarino.Draw;
import davidmarino.service.Parameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class MainController {

    @PostMapping("dungeon")
    public APIGatewayProxyResponseEvent handleDungeonPost(APIGatewayProxyRequestEvent input, Context context) {
        try {
            Parameters parameters = new ObjectMapper().readValue(input.getBody(), Parameters.class);
            Draw draw = new Draw(parameters);
            byte[] imageBytes = draw.runDungeon();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(Map.of(
                            "Content-Type", "image/png",
                            "Access-Control-Allow-Origin", "*"
                    ))
                    .withBody(base64Image)
                    .withIsBase64Encoded(true); // Important!
        } catch (Exception e) {
            return buildResponse("Error: " + e.getMessage(), 500);
        }
    }

    @PostMapping("/map")
    public APIGatewayProxyResponseEvent handleMapPost(APIGatewayProxyRequestEvent input, Context context) {
        try {
            Parameters parameters = new ObjectMapper().readValue(input.getBody(), Parameters.class);
            Draw draw = new Draw(parameters);
            byte[] imageBytes = draw.runMap();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(Map.of(
                            "Content-Type", "image/png",
                            "Access-Control-Allow-Origin", "*"
                    ))
                    .withBody(base64Image)
                    .withIsBase64Encoded(true);
        } catch (Exception e) {
            return buildResponse("Error: " + e.getMessage(), 500);
        }
    }

    public APIGatewayProxyResponseEvent buildResponse(String output, int statusCode) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(headers)
                .withBody(output);
    }
}
