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
                "Content-Type", "application/json"
        );
    }

}
