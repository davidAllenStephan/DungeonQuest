package davidmarino.dungeon.dungeonservice;

import davidmarino.dungeon.dungeonmodels.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {
    /**
     * Need to account for distance from other tiles. Time efficient method would be to iterate
     * through the quadrants of the image. Placing the zone randomly in that quadrant and the zone will
     * record its sub quadrant. You can then randomly place points without risking any overlapping.
     * @param tileCanvas
     * @param numberOfRooms
     * @param minimumRoomWidth
     * @param minimumRoomHeight
     * @param maximumRoomWidth
     * @param maximumRoomHeight
     */
    public void generateRooms(TileCanvas tileCanvas, int numberOfRooms, int minimumRoomWidth, int minimumRoomHeight, int maximumRoomWidth, int maximumRoomHeight) {
        Random random = new Random();
        int i = 0;
        while (i < numberOfRooms) {
            int rX = random.nextInt(tileCanvas.width);
            int rY = random.nextInt(tileCanvas.height);
            int rWidth = random.nextInt(minimumRoomWidth, maximumRoomWidth);
            int rHeight = random.nextInt(minimumRoomHeight, maximumRoomHeight);
            int leftMargin = 16;
            int leftX = rX - (rWidth / 2);
            int rightMargin = tileCanvas.width - 16;
            int rightX = rX + (rWidth / 2);
            int topMargin = tileCanvas.height - 16;
            int topY = rY + (rHeight / 2);
            int bottomMargin = 16;
            int bottomY = rY - (rHeight / 2);
            if (leftX > leftMargin && rightX < rightMargin && topY < topMargin && bottomY > bottomMargin) {
                tileCanvas.toZone(tileCanvas.find(rX, rY), rWidth, rHeight, ZoneType.ZONE);
            }
            tileCanvas.find(rX, rY).zoneType = ZoneType.SITE;
            i++;
        }
    }
}
