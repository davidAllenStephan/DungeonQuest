package davidmarino.controller;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import davidmarino.model.Parameters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ControllerUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Parameters parseParameters(String body) throws IOException {
        return objectMapper.readValue(body, Parameters.class);
    }

    public static ResponseEntity getStringResponse(String message, int statusCode) {
        return ResponseEntity.status(statusCode).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(message);
    }

    public static ResponseEntity getPngResponse(byte[] imageBytes) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"generated.png\"")
                .body(imageBytes);
    }

    public static APIGatewayProxyResponseEvent getLambdaPngResponse(String base64Image) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(getDefaultHeaders())
                .withBody(base64Image)
                .withIsBase64Encoded(true);
    }

    public static APIGatewayProxyResponseEvent getLambdaStringResponse(String message, int statusCode) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(getDefaultHeaders())
                .withBody(message);
    }

    public static Map<String, String> getDefaultHeaders() {
        return Map.of(
                "Access-Control-Allow-Headers", "Content-Type",
                "Access-Control-Allow-Methods", "OPTIONS,POST",
                "Access-Control-Allow-Origin", "*",
                "Content-Type", "image/png"
        );
    }

}
