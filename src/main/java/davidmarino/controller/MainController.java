package davidmarino.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import davidmarino.Draw;
import davidmarino.service.Parameters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class MainController {

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

    public APIGatewayProxyResponseEvent handleGetRequest(APIGatewayProxyRequestEvent input, Context context) {
        Gson gson = new Gson();
//        Parameters parameters = gson.fromJson(input.getBody(), Parameters.class);
//        Draw draw = new Draw(parameters);
//        byte[] imageBytes = draw.runMap();
//        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        return buildResponse(gson.toJson(input.getBody()), 200);
    }

    public APIGatewayProxyResponseEvent buildResponse(String output, int statusCode) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("HELLO", "HELLO");
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(headers)
                .withBody(output);
    }
}
