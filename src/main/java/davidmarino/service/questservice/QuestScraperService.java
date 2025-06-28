package davidmarino.service.questservice;

import davidmarino.model.questmodels.MonsterCollection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public static MonsterCollection getQuest() {
        Document doc = getDocument("https://list.fandom.com/wiki/List_of_monsters");
        Element body = doc.body();
        questService.removeBlank(body);
        HashMap<Element, Integer> lineNumberMap = questService.getLineNumbers(body);
        Elements headlines = questService.getByClassName(body, "mw-headline", 2, 67);
        Elements monsters = questService.getByTag(body, "a", 210, 42);
        MonsterCollection mc = MonsterCollectionService.objectify(headlines, monsters, lineNumberMap);
        return mc;
    }
}
