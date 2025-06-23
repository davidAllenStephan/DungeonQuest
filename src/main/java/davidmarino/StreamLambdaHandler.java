package davidmarino;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import davidmarino.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class StreamLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    MainController controller;

    public StreamLambdaHandler() {
        ApplicationContext context = SpringApplication.run(Application.class);
        this.controller = context.getBean(MainController.class);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        if (input == null || input.getPath() == null || input.getHttpMethod() == null) {
            return controller.buildResponse("Invalid request: missing path or method", 400);
        }

        String path = input.getPath();
        String httpMethod = input.getHttpMethod();

        switch (path) {
            case "/dungeon":
                if ("POST".equals(httpMethod)) {
                    return controller.handleDungeonPost(input, context);
                } else {
                    return controller.buildResponse("Invalid HTTP method for /dungeon", 400);
                }

            case "/map":
                if ("POST".equals(httpMethod)) {
                    return controller.handleMapPost(input, context);
                } else {
                    return controller.buildResponse("Invalid HTTP method for /map", 400);
                }

            default:
                return controller.buildResponse("Invalid path: " + path, 404);
        }
    }

}