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
        return controller.handleGetRequest(input, context);
    }
}