package davidmarino.model.questmodels;

import com.google.gson.Gson;
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
public class QuestScraper {

    private static final Logger logger = LoggerFactory.getLogger(QuestScraper.class);
    QuestService service;

    public QuestScraper(QuestService service) {
        this.service= service;
    }

    public static String exportMonsterCollectionToJson() {


        Gson gson = new Gson();
        return gson.toJson(mc);

    }

    public static String getQuest() {

    }

}
