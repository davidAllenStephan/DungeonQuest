package davidmarino.dungeon.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.Parameters;
import davidmarino.dungeon.dungeonservice.DungeonService;
import davidmarino.dungeon.dungeonviews.DungeonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dungeon {
    public transient TileCanvas zones;
    @JsonProperty("dungeon_image")
    public String dungeonImage;

    @Autowired
    private transient DungeonView dungeonView = new DungeonView();
    @Autowired
    private transient DungeonService dungeonService = new DungeonService();

    public Dungeon() {
        zones = new TileCanvas();
    }

    public Dungeon(int numberOfRooms, int minimumRoomWidth, int minimumRoomHeight, int maximumRoomWidth, int maximumRoomHeight) {
        zones = dungeonService.getZones(numberOfRooms, minimumRoomWidth, minimumRoomHeight, maximumRoomWidth, maximumRoomHeight);
        dungeonService.connectSites(zones);
        dungeonImage = dungeonView.getBase64(zones);
    }

    public Dungeon(Parameters parameters) {
        zones = dungeonService.getZones(parameters.numberOfRooms, parameters.minimumRoomWidth, parameters.minimumRoomHeight, parameters.maximumRoomWidth, parameters.maximumRoomHeight);
        dungeonImage = dungeonView.getBase64(zones);
    }
}
