package davidmarino.service.questservice;

import davidmarino.model.questmodels.Monster;
import davidmarino.model.questmodels.MonsterCategory;
import davidmarino.model.questmodels.MonsterCollection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class QuestService {

    public QuestService() {

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
