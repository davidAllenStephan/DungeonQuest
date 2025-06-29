package davidmarino.model.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.model.Parameters;
import davidmarino.service.dungeonservice.DungeonService;
import davidmarino.view.dungeonviews.DungeonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Dungeon {
    public transient ArrayList<Zone> zones;
    @JsonProperty("dungeon_image")
    public String dungeonImage;

    @Autowired
    private transient DungeonView dungeonView;
    @Autowired
    private transient DungeonService dungeonService;

    public Dungeon() {
        dungeonView = new DungeonView();
        dungeonService = new DungeonService();
        zones = new ArrayList<>();
    }

    public Dungeon(Parameters parameters) {
        dungeonView = new DungeonView();
        dungeonService = new DungeonService();
        zones = dungeonService.getZones(parameters.numberOfRooms, parameters.minimumRoomWidth, parameters.minimumRoomHeight, parameters.maximumRoomWidth, parameters.maximumRoomHeight);
        dungeonImage = dungeonView.getBase64(zones, parameters.backgroundColor, parameters.edgeSize);
    }
}
