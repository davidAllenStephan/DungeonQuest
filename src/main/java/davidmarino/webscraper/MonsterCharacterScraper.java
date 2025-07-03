package davidmarino.webscraper;

import davidmarino.quest.questmodels.Monster;
import davidmarino.quest.questmodels.MonsterCategory;
import davidmarino.quest.questmodels.MonsterCollection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MonsterCharacterScraper {

    @Autowired
    private final QuestScraper questScraper = new QuestScraper();

    public static MonsterCollection objectify(Elements elementsToMonsterCategories, Elements elementsToMonsterIndividuals, HashMap<Element, Integer> lineNumbers) {
        MonsterCollection monsterCollection = new MonsterCollection();
        ArrayList<Element> categoryElements = new ArrayList<>(elementsToMonsterCategories);
        ArrayList<MonsterCategory> monsterCategories = new ArrayList<>();
        Map<Element, MonsterCategory> elementToMonsterCategory = new HashMap<>();
        for (Element element : categoryElements) {
            String categoryName = element.text();
            MonsterCategory monsterCategory = new MonsterCategory(categoryName);
            monsterCategories.add(monsterCategory);
            elementToMonsterCategory.put(element, monsterCategory);
        }
        for (Element element : elementsToMonsterIndividuals) {
            int monsterLine = lineNumbers.get(element);
            MonsterCategory targetCategory = null;
            for (Element _element : categoryElements) {
                int catLine = lineNumbers.get(_element);
                if (catLine < monsterLine) {
                    targetCategory = elementToMonsterCategory.get(_element);
                } else {
                    break;
                }
            }
            if (targetCategory != null) {
                String monsterName = element.text();
                if (!monsterName.isBlank()) {
                    Monster monster = new Monster(monsterName);
                    targetCategory.addMonster(monster);
                }
            }
        }
        for (MonsterCategory category : monsterCategories) {
            monsterCollection.addCategory(category);
        }
        return monsterCollection;
    }

    public MonsterCollection getMonsterCollection() {
        Document doc = QuestScraper.getDocument("https://list.fandom.com/wiki/List_of_monsters");
        Element body = doc.body();
        questScraper.removeBlank(body);
        HashMap<Element, Integer> lineNumberMap = questScraper.getLineNumbers(body);
        Elements headlines = questScraper.getByClassName(body, "mw-headline", 2, 67);
        Elements monsters = questScraper.getByTag(body, "a", 210, 42);
        MonsterCollection mc = MonsterCharacterScraper.objectify(headlines, monsters, lineNumberMap);
        return mc;
    }
}
