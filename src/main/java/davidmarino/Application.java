package davidmarino;

import davidmarino.quest.questmodels.ArtifactCollection;
import davidmarino.quest.questmodels.MajorCharacterCollection;
import davidmarino.quest.questmodels.MonsterCollection;
import davidmarino.webscraper.ArtifactScraper;
import davidmarino.webscraper.MajorCharacterScraper;
import davidmarino.webscraper.MonsterCharacterScraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
        MonsterCharacterScraper monsterCharacterScraper = new MonsterCharacterScraper();
        MonsterCollection monsterCollection = monsterCharacterScraper.getMonsterCollection();
        int sum = 0;
        for (int i = 0; i < monsterCollection.getMonsterCategories().size(); i++) {
            for (int j = 0; j < monsterCollection.getMonsterCategories().get(i).getMonsters().size(); j++) {
                sum += 1;
            }
        }
        System.out.println(sum);
    }
}
