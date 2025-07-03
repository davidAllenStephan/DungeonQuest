package davidmarino.webscraper;

import davidmarino.quest.questmodels.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MajorCharacterScraper {

    @Autowired
    private final QuestScraper questScraper = new QuestScraper();

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(MajorCharacterScraper.class);

    public static VillainCharacterCollection objectify() {
        return new VillainCharacterCollection();
    }

    public MajorCharacterCollection getBossCollection() {
        Document doc = QuestScraper.getDocument("https://en.wikipedia.org/wiki/AFI%27s_100_Years...100_Heroes_%26_Villains");
        Element body = doc.body();
        Elements heroesAndVillains = questScraper.getByTag(body, "td", 14, 0);
        List<Element> heroes = heroesAndVillains.subList(0, 305);
        List<Element> villains = heroesAndVillains.subList(304, heroesAndVillains.size());
        HeroCharacterCollection heroCollection = new HeroCharacterCollection();
        VillainCharacterCollection villainCollection = new VillainCharacterCollection();

        ArrayList<String> heroStrings = new ArrayList<>();
        for (Element hero : heroes) {
            heroStrings.add(hero.text());
        }
        int i = 0;
        int j = 0;
        ArrayList<String> subHeroStrings = new ArrayList<>();
        while ((j * 6) + i != heroes.size()) {
            if (i == 6) {
                heroCollection.majorCharacters.add(new HeroCharacter(subHeroStrings.get(0),
                        subHeroStrings.get(1),
                        subHeroStrings.get(2),
                        subHeroStrings.get(3),
                        subHeroStrings.get(4),
                        subHeroStrings.get(5)));
                i = 0;
                j++;
                subHeroStrings.clear();
            } else {
                subHeroStrings.add(heroStrings.get((j * 6) + i));
                i++;
            }
        }

        ArrayList<String> villainStrings = new ArrayList<>();
        for (Element villain : villains) {
            villainStrings.add(villain.text());
        }
        int k = 0;
        int l = 0;
        ArrayList<String> subVillainStrings = new ArrayList<>();
        while ((l * 6) + k != villains.size()) {
            if (k == 6) {
                villainCollection.majorCharacters.add(new VillainCharacter(subVillainStrings.get(0),
                        subVillainStrings.get(1),
                        subVillainStrings.get(2),
                        subVillainStrings.get(3),
                        subVillainStrings.get(4),
                        subVillainStrings.get(5)));
                k = 0;
                l++;
                subVillainStrings.clear();
            } else {
                subVillainStrings.add(villainStrings.get((l * 6) + k));
                k++;
            }
        }

        logger.info("villain: " + villainCollection.majorCharacters.toString());
        logger.info("count: " + villainCollection.majorCharacters.size());

//        logger.info("hero: " + heroCollection.majorCharacters.toString());
//        logger.info("count: " + heroCollection.majorCharacters.size());

        return MajorCharacterScraper.objectify();
    }

}
