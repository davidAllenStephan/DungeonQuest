package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.enums.DungeonShape;
import davidmarino.dungeon.dungeonmodels.TileCanvas;
import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import org.springframework.stereotype.Service;

@Service
public class DungeonService {

    TileCanvasService tileCanvasService = new TileCanvasService();

    public DungeonService() {

    }

    public TileCanvas getRooms(int maximumRoomWidth, int maximumRoomHeight, DungeonType dungeonType) {
        TileCanvas room = new TileCanvas(maximumRoomWidth, maximumRoomHeight, dungeonType);
        tileCanvasService.setTileCanvas(room);
        tileCanvasService.setDungeonShape(DungeonShape.SQUARE);

        tileCanvasService.setWalls();
        tileCanvasService.setFloors();
        tileCanvasService.setBorders();
        tileCanvasService.setDecorations();

        return room;
    }
}
