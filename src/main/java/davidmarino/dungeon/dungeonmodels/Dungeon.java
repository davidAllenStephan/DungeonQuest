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

    private final transient DungeonView dungeonView = new DungeonView();
    private final transient DungeonService dungeonService = new DungeonService();

    public Dungeon() {
        zones = new TileCanvas();
    }

    public Dungeon(int maximumRoomWidth, int maximumRoomHeight) {
        zones = dungeonService.getZones(maximumRoomWidth, maximumRoomHeight);
        dungeonImage = dungeonView.getBase64(zones);
    }

    public Dungeon(Parameters parameters) {
        zones = dungeonService.getZones(parameters.maximumRoomWidth, parameters.maximumRoomHeight);
        dungeonImage = dungeonView.getBase64(zones);
    }
}
