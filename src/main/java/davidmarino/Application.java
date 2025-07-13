package davidmarino;

import davidmarino.dungeon.dungeonmodels.Dungeon;
import davidmarino.dungeon.dungeonmodels.DungeonCollection;
import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import davidmarino.dungeon.dungeonservice.DungeonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
        Dungeon dungeon = new Dungeon(10, 10, DungeonType.HEALTH);
        System.out.println(dungeon.dungeonImage);
    }
}
