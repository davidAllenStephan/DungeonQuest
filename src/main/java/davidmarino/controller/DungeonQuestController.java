package davidmarino.controller;

import davidmarino.model.DungeonQuest;
import davidmarino.model.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class DungeonQuestController {

    private final static Logger logger = LoggerFactory.getLogger(DungeonQuestController.class);

    @PostMapping("/")
    public ResponseEntity dungeonQuest(@RequestBody Parameters parameters) {
        DungeonQuest dungeonQuest = new DungeonQuest(parameters);
        return ControllerUtil.getStringResponse(dungeonQuest.getJson(), 200);
    }

}
