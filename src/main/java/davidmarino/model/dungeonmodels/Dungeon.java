package davidmarino.model.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.model.Parameters;
import davidmarino.service.dungeonservice.DungeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Dungeon {
    @JsonProperty("zones")
    public transient ArrayList<Zone> zones;
    @JsonProperty("dungeon_image")
    public String dungeonImage;

    @Autowired
    private transient DungeonService dungeonService;

    public Dungeon() {

    }

    public Dungeon(ArrayList<Zone> zones) {
        this.zones = zones;
    }

    public Dungeon(Parameters parameters) {
        dungeonService = new DungeonService(parameters);
        zones = dungeonService.getZones();
        dungeonImage = dungeonService.getBase64(zones);
    }
}
