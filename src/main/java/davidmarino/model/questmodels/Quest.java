package davidmarino.model.questmodels;

import com.google.gson.Gson;
import davidmarino.service.Exporter;
import davidmarino.service.questservice.QuestService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class Quest {

    private static final Logger logger = LoggerFactory.getLogger(Quest.class);
    QuestService service;

    public Quest(QuestService service) {
        this.service= service;
    }

    public static String exportMonsterCollectionToJson() {
        QuestService service = new QuestService();
        Document doc = null;

        try {
            doc = Jsoup.connect("https://list.fandom.com/wiki/List_of_monsters").get();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        Element body = doc.body();
        service.removeBlank(body);

        HashMap<Element, Integer> lineNumberMap = service.getLineNumbers(body);

        Elements headlines = service.getByClassName(body, "mw-headline", 2, 67);
        Elements monsters = service.getByTag(body, "a", 210, 42);

        MonsterCollection mc = service.objectify(headlines, monsters, lineNumberMap);

        Gson gson = new Gson();
        return gson.toJson(mc);

    }

}
