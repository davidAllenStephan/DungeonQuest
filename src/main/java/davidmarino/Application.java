package davidmarino;

import davidmarino.service.questservice.MajorCollectionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
        MajorCollectionService majorCollectionService = new MajorCollectionService();
        majorCollectionService.getBossCollection();
    }
}
