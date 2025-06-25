package davidmarino.webscraper;

import davidmarino.model.Monster;
import davidmarino.model.MonsterCategory;
import davidmarino.model.MonsterCollection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class WebScraper {

    private static final Logger logger = LoggerFactory.getLogger(WebScraper.class);

    private static HashMap<Element, Integer> getLineNumbers(Element elements) {
        HashMap<Element, Integer> lineNumbers = new HashMap<>();
        int i = 0;
        for (Element element: elements) {
            lineNumbers.put(element, i);
            i++;
        }
        return lineNumbers;
    }

    private static void trim(int removeFromFront, int removeFromEnd, Elements elements) {
        ArrayList<Element> _elements = new ArrayList<>();
        _elements.addAll(elements.subList(0, removeFromFront));
        _elements.addAll(elements.subList(elements.size() - removeFromEnd, elements.size()));
        elements.removeAll(_elements);
    }

    private static Elements getByClassName(Element element, String className, int trimFromFront, int trimFromEnd) {
        Elements elements = element.getElementsByClass(className);
        trim(trimFromFront, trimFromEnd, elements);
        return elements;
    }

    private static Elements getByTag(Element element, String tagName, int trimFromFront, int trimFromEnd) {
        Elements elements = element.getElementsByTag(tagName);
        trim(trimFromFront, trimFromEnd, elements);
        return elements;
    }

    private static void removeBlank(Elements elements) {
        elements = new Elements(
                elements.stream()
                        .filter(e -> !e.text().isBlank())
                        .toList()
        );
    }

    private static void removeBlank(Element element) {
        removeBlank(element.getAllElements());
    }

    private static MonsterCollection objectify(Elements monsterCategories, Elements monsters, HashMap<Element, Integer> lineNumbers) {
        MonsterCollection mc = new MonsterCollection();
        ArrayList<Element> categoryElements = new ArrayList<>(monsterCategories);
        ArrayList<MonsterCategory> categories = new ArrayList<>();
        Map<Element, MonsterCategory> elementToCategory = new HashMap<>();

        for (Element catElem : categoryElements) {
            String categoryName = catElem.text();
            MonsterCategory category = new MonsterCategory(categoryName);
            categories.add(category);
            elementToCategory.put(catElem, category);
        }

        for (Element monsterElem : monsters) {
            int monsterLine = lineNumbers.get(monsterElem);
            MonsterCategory targetCategory = null;
            for (Element catElem : categoryElements) {
                int catLine = lineNumbers.get(catElem);
                if (catLine < monsterLine) {
                    targetCategory = elementToCategory.get(catElem);
                } else {
                    break;
                }
            }
            if (targetCategory != null) {
                String monsterName = monsterElem.text();
                if (!monsterName.isBlank()) {
                    Monster monster = new Monster(monsterName);
                    targetCategory.addMonster(monster);
                }
            }
        }

        for (MonsterCategory category : categories) {
            mc.addCategory(category);
        }

        return mc;
    }




    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://list.fandom.com/wiki/List_of_monsters").get();
            Element body = doc.body();

            removeBlank(body);

            HashMap<Element, Integer> lineNumberMap = getLineNumbers(body);

            Elements headlines = getByClassName(body, "mw-headline", 2, 67);
            Elements monsters = getByTag(body, "a", 210, 42);

            MonsterCollection mc = objectify(headlines, monsters, lineNumberMap);
            logger.info("mc " + mc);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
