package davidmarino;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import davidmarino.api.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreamLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(StreamLambdaHandler.class);
    private final Router router = new Router();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        String path = request.getPath();
        String httpMethod = request.getHttpMethod();
        String body = request.getBody();
        try {
            return router.route(request, context);
        } catch (Exception e) {
            logger.error(e.toString());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("path: " + path + "\nhttpMethod: " +  httpMethod + "\nbody: " + body + "\nError in StreamLambdaHandler: " + e.toString());
        }
    }
}