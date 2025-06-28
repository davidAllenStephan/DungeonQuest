package davidmarino.service.questservice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class QuestScraperService {

    @Autowired
    private static QuestService questService = new QuestService();

    @Autowired
    public static final Logger logger = LoggerFactory.getLogger(QuestScraperService.class);

    public static Document getDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://list.fandom.com/wiki/List_of_monsters").get();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return doc;
    }

    public void trim(int removeFromFront, int removeFromEnd, Elements elements) {
        ArrayList<Element> _elements = new ArrayList<>();
        _elements.addAll(elements.subList(0, removeFromFront));
        _elements.addAll(elements.subList(elements.size() - removeFromEnd, elements.size()));
        elements.removeAll(_elements);
    }

    public Elements getByClassName(Element element, String className, int trimFromFront, int trimFromEnd) {
        Elements elements = element.getElementsByClass(className);
        trim(trimFromFront, trimFromEnd, elements);
        return elements;
    }

    public Elements getByTag(Element element, String tagName, int trimFromFront, int trimFromEnd) {
        Elements elements = element.getElementsByTag(tagName);
        trim(trimFromFront, trimFromEnd, elements);
        return elements;
    }

    public void removeBlank(Elements elements) {
        elements = new Elements(
                elements.stream()
                        .filter(e -> !e.text().isBlank())
                        .toList()
        );
    }

    public void removeBlank(Element element) {
        removeBlank(element.getAllElements());
    }

    public HashMap<Element, Integer> getLineNumbers(Element elements) {
        HashMap<Element, Integer> lineNumbers = new HashMap<>();
        int i = 0;
        for (Element element: elements) {
            lineNumbers.put(element, i);
            i++;
        }
        return lineNumbers;
    }
}
