package davidmarino.model.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.service.dungeonservice.DungeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Dungeon {
    @JsonProperty("zones")
    ArrayList<Zone> zones;
    @JsonProperty("dungeon_image")
    String dungeonImage;

    @Autowired
    private DungeonService dungeonService;

    public Dungeon(ArrayList<Zone> zones) {
        this.zones = zones;
    }

    public Dungeon() {
        zones = dungeonService.getZones();
        dungeonImage = dungeonService.getBase64(zones);
    }
}
