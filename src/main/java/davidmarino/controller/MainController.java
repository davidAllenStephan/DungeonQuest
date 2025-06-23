package davidmarino.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import davidmarino.Draw;
import davidmarino.model.Parameters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class MainController {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, String> getDefaultHeaders() {
        return Map.of(
                "Access-Control-Allow-Headers", "Content-Type",
                "Access-Control-Allow-Methods", "OPTIONS,POST",
                "Access-Control-Allow-Origin", "*",
                "Content-Type", "image/png"
        );
    }

    @PostMapping("/dungeon")
    public ResponseEntity<byte[]> dungeon(@RequestBody Parameters parameters) {
        Draw draw = new Draw(parameters);
        byte[] imageBytes = draw.runDungeon();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"generated.png\"")
                .body(imageBytes);
    }

    @PostMapping("/map")
    public ResponseEntity<byte[]> map(@RequestBody Parameters parameters) {
        Draw draw = new Draw(parameters);
        byte[] imageBytes = draw.runMap();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"generated.png\"")
                .body(imageBytes);
    }

    public APIGatewayProxyResponseEvent handleDungeonPost(APIGatewayProxyRequestEvent input, Context context) {
        return generateImageResponse(input.getBody(), true);
    }

    @PostMapping("/map")
    public APIGatewayProxyResponseEvent handleMapPost(APIGatewayProxyRequestEvent input, Context context) {
        return generateImageResponse(input.getBody(), false);
    }

    private APIGatewayProxyResponseEvent generateImageResponse(String requestBody, boolean isDungeon) {
        try {
            Parameters parameters = objectMapper.readValue(requestBody, Parameters.class);
            Draw draw = new Draw(parameters);
            byte[] imageBytes = isDungeon ? draw.runDungeon() : draw.runMap();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(getDefaultHeaders())
                    .withBody(base64Image)
                    .withIsBase64Encoded(true);
        } catch (Exception e) {
            return buildErrorResponse("Error: " + e.getMessage(), 500);
        }
    }

    public APIGatewayProxyResponseEvent buildResponse(String output, int statusCode) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(Map.of(
                        "Access-Control-Allow-Headers", "Content-Type",
                        "Access-Control-Allow-Methods", "OPTIONS,POST",
                        "Access-Control-Allow-Origin", "*",
                        "Content-Type", "image/png"
                ))
                .withBody(output);
    }

    public APIGatewayProxyResponseEvent buildErrorResponse(String message, int statusCode) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(getDefaultHeaders())
                .withBody(message);
    }
}