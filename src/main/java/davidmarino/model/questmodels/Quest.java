package davidmarino.model.questmodels;

import com.google.gson.Gson;
import davidmarino.service.questservice.QuestScraperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Quest {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(Quest.class);

    public static String getQuestJson() {
        MonsterCollection mc = QuestScraperService.getQuest();
        Gson gson = new Gson();
        return gson.toJson(mc);
    }

    public static String getQuestFromDungeonJson() {
    }

}
