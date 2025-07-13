package davidmarino.dungeon.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.Parameters;
import davidmarino.dungeon.dungeonmodels.enums.DungeonShape;
import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import davidmarino.dungeon.dungeonservice.DungeonService;
import davidmarino.dungeon.dungeonviews.DungeonView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
public class Dungeon {

    @JsonProperty("room_images")
    public ArrayList<String> roomImages;

    @JsonProperty("room_types")
    public ArrayList<DungeonType> roomTypes;

    private final transient DungeonView dungeonView = new DungeonView();
    private final transient DungeonService dungeonService = new DungeonService();

    public Dungeon() {
        roomImages = null;
    }

    public Dungeon(int maximumRoomWidth, int maximumRoomHeight, DungeonType dungeonType, DungeonShape dungeonShape) {
        TileCanvas room = dungeonService.getRooms(maximumRoomWidth, maximumRoomHeight, dungeonType, dungeonShape);
        switch (dungeonType) {
            case TREASURE:
                roomImages.add(dungeonView.getBase64(room, 12, 30));
                break;
            case FLOWER:
                roomImages.add(dungeonView.getBase64(room, 20, 20));
                break;
            case STRENGTH:
                roomImages.add(dungeonView.getBase64(room, 0, 3));
                break;
            case PUZZLE:
                roomImages.add(dungeonView.getBase64(room, 0, 0));
                break;
            case HEALTH:
                roomImages.add(dungeonView.getBase64(room, 14, 2));
                break;
            case SECRET:
                roomImages.add(dungeonView.getBase64(room, 38, 8));
                break;
            case BOSS:
                roomImages.add(dungeonView.getBase64(room, 27, 51));
                break;
        }
    }

    public Dungeon(Parameters parameters) {
        Random random = new Random();
        roomImages = new ArrayList<>();
        roomTypes = new ArrayList<>();
        for (int i = 0; i < parameters.numberOfRooms; i++) {
            DungeonType dungeonType = DungeonType.values()[random.nextInt(0, DungeonType.values().length)];
            DungeonShape dungeonShape = DungeonShape.values()[random.nextInt(0, DungeonShape.values().length)];
            TileCanvas room = dungeonService.getRooms(parameters.maximumRoomWidth, parameters.maximumRoomHeight, dungeonType, dungeonShape);
            roomTypes.add(dungeonType);
            switch (dungeonType) {
                case TREASURE:
                    roomImages.add(dungeonView.getBase64(room, 12, 30));
                    break;
                case FLOWER:
                    roomImages.add(dungeonView.getBase64(room, 20, 20));
                    break;
                case STRENGTH:
                    roomImages.add(dungeonView.getBase64(room, 0, 3));
                    break;
                case PUZZLE:
                    roomImages.add(dungeonView.getBase64(room, 0, 0));
                    break;
                case HEALTH:
                    roomImages.add(dungeonView.getBase64(room, 14, 2));
                    break;
                case SECRET:
                    roomImages.add(dungeonView.getBase64(room, 38, 8));
                    break;
                case BOSS:
                    roomImages.add(dungeonView.getBase64(room, 27, 51));
                    break;
            }
        }
    }
}
