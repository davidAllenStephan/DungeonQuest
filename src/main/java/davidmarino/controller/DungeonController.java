package davidmarino.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableWebMvc
public class DungeonController {

    private final static Logger logger = LoggerFactory.getLogger(DungeonController.class);
//
//    @PostMapping("/dungeon")
//    public ResponseEntity dungeon(@RequestBody Parameters parameters) {
//        DungeonService dungeonService = new DungeonService(parameters);
//        byte[] imageBytes = dungeonService.runDungeon();
//        return ControllerUtil.getPngResponse(imageBytes);
//    }

}
