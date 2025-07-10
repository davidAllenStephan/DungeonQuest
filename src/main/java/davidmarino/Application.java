package davidmarino;

import davidmarino.dungeon.dungeonmodels.Dungeon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
        System.out.println("Starting dungeon creation");
        Dungeon dungeon = new Dungeon(10, 50, 50, 100, 100);
        System.out.println(dungeon.dungeonImage);
    }
}
