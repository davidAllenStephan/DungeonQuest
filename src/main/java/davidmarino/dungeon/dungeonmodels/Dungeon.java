package davidmarino.dungeon.dungeonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.Parameters;
import davidmarino.dungeon.dungeonmodels.enums.DungeonShape;
import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import davidmarino.dungeon.dungeonservice.DungeonMapService;
import davidmarino.dungeon.dungeonservice.DungeonService;
import davidmarino.dungeon.dungeonservice.TileFactory;
import davidmarino.dungeon.dungeonviews.DungeonView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
public class Dungeon {

    @JsonProperty("dungeon_image")
    public String dungeonImage;

    @JsonProperty("room_images")
    public ArrayList<String> roomImages;

    @JsonProperty("room_types")
    public ArrayList<DungeonType> roomTypes;

    private final transient DungeonView dungeonView = new DungeonView();
    private final transient DungeonService dungeonService = new DungeonService();

    public Dungeon() {
        roomImages = null;
    }

    public Dungeon(int numberOfRooms, int minimumRoomWidth, int minimumRoomHeight, int maximumRoomWidth, int maximumRoomHeight, TileFactory tileFactory) {
        Random random = new Random();
        roomImages = new ArrayList<>();
        roomTypes = new ArrayList<>();
        for (int i = 0; i < numberOfRooms; i++) {
            DungeonType dungeonType = DungeonType.values()[random.nextInt(0, DungeonType.values().length)];
            DungeonShape dungeonShape = DungeonShape.values()[random.nextInt(0, DungeonShape.values().length)];
            TileCanvas room = dungeonService.getRooms(maximumRoomWidth, maximumRoomHeight, dungeonType, dungeonShape, tileFactory);
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
        ArrayList<Room> rooms = DungeonMapService.generateRooms(roomTypes, numberOfRooms, 400, 400, minimumRoomWidth, minimumRoomHeight, maximumRoomWidth, maximumRoomHeight);
        DungeonMapService.connectRooms(rooms);
        dungeonImage = dungeonView.getBase64(rooms);
    }

    public Dungeon(Parameters parameters, TileFactory tileFactory) {
        Random random = new Random();
        roomImages = new ArrayList<>();
        roomTypes = new ArrayList<>();
        for (int i = 0; i < parameters.numberOfRooms; i++) {
            DungeonType dungeonType = DungeonType.values()[random.nextInt(0, DungeonType.values().length)];
            DungeonShape dungeonShape = DungeonShape.values()[random.nextInt(0, DungeonShape.values().length)];
            TileCanvas room = dungeonService.getRooms(parameters.maximumRoomWidth, parameters.maximumRoomHeight, dungeonType, dungeonShape, tileFactory);
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
        ArrayList<Room> rooms = DungeonMapService.generateRooms(roomTypes, parameters.numberOfRooms, 400, 400, parameters.minimumRoomWidth, parameters.minimumRoomHeight, parameters.maximumRoomWidth, parameters.maximumRoomHeight);
        DungeonMapService.connectRooms(rooms);
        dungeonImage = dungeonView.getBase64(rooms);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dungeon {");

        sb.append("\n  roomTypes: ");
        if (roomTypes != null && !roomTypes.isEmpty()) {
            sb.append("[");
            for (int i = 0; i < roomTypes.size(); i++) {
                sb.append(roomTypes.get(i));
                if (i < roomTypes.size() - 1) sb.append(", ");
            }
            sb.append("]");
        } else {
            sb.append("null");
        }

        sb.append(",\n  roomImages: ");
        if (roomImages != null) {
            sb.append("[");
            for (int i = 0; i < roomImages.size(); i++) {
                sb.append(roomImages.get(i));
            }
        } else {
            sb.append("null");
        }

        sb.append("\n}");
        return sb.toString();
    }

}
