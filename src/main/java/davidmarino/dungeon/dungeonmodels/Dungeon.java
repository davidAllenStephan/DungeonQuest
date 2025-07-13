package davidmarino.dungeon.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.Parameters;
import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import davidmarino.dungeon.dungeonservice.DungeonService;
import davidmarino.dungeon.dungeonviews.DungeonView;
import org.springframework.stereotype.Component;

@Component
public class Dungeon {
    public transient TileCanvas rooms;

    @JsonProperty("dungeon_image")
    public String dungeonImage;

    @JsonProperty("dungeonType")
    public DungeonType dungeonType;

    private final transient DungeonView dungeonView = new DungeonView();
    private final transient DungeonService dungeonService = new DungeonService();

    public Dungeon() {
        dungeonType = DungeonType.EMPTY;
        rooms = new TileCanvas();
    }

    public Dungeon(int maximumRoomWidth, int maximumRoomHeight, DungeonType dungeonType) {
        this.dungeonType = dungeonType;
        rooms = dungeonService.getRooms(maximumRoomWidth, maximumRoomHeight, dungeonType);
        dungeonImage = dungeonView.getBase64(rooms);
    }

    public Dungeon(Parameters parameters) {
        dungeonType = DungeonType.EMPTY;
        rooms = dungeonService.getRooms(parameters.maximumRoomWidth, parameters.maximumRoomHeight, dungeonType);
        dungeonImage = dungeonView.getBase64(rooms);
    }
}
