package davidmarino.service.questservice;

import davidmarino.model.questmodels.Monster;
import davidmarino.model.questmodels.MonsterCategory;
import davidmarino.model.questmodels.MonsterCollection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonsterCollectionService {

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
}
