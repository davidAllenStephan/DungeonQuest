package davidmarino.dungeon.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.Parameters;
import davidmarino.dungeon.dungeonservice.DungeonService;
import davidmarino.dungeon.dungeonviews.DungeonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Dungeon {
    public transient TileCanvas zones;
    @JsonProperty("dungeon_image")
    public String dungeonImage;

    @Autowired
    private transient DungeonView dungeonView;
    @Autowired
    private transient DungeonService dungeonService;

    public Dungeon() {
        dungeonView = new DungeonView();
        dungeonService = new DungeonService();
        zones = new TileCanvas();
    }

    public Dungeon(Parameters parameters) {
        dungeonView = new DungeonView();
        dungeonService = new DungeonService();
        zones = dungeonService.getZones(parameters.numberOfRooms, parameters.minimumRoomWidth, parameters.minimumRoomHeight, parameters.maximumRoomWidth, parameters.maximumRoomHeight);
        dungeonImage = dungeonView.getBase64(zones, parameters.backgroundColor, parameters.edgeSize);
    }
}
