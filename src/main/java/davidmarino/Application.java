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

        MajorCharacterScraper majorCharacterScraper = new MajorCharacterScraper();
        MajorCharacterCollection mcc = majorCharacterScraper.getMajorCharacterCollection();
        mcc.uploadToDynamoDB();

        MonsterCharacterScraper monsterCharacterScraper = new MonsterCharacterScraper();
        MonsterCollection mc = monsterCharacterScraper.getMonsterCollection();
        mc.uploadToDynamoDB();

        ArtifactScraper artifactScraper = new ArtifactScraper();
        ArtifactCollection ac = artifactScraper.getArtifactCollection();
        ac.uploadToDynamoDB();
    }
}
