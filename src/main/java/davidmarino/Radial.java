/**
 * Authors: David Allen Stephan Marino
 * Date: 6/14/25
 */

package davidmarino;

import java.util.Random;

public class Radial {
    public void adjustToRadial(Point point) {
        double midX = Parameters.width / 2;
        double midY = Parameters.height / 2;
        double distFromMidToCorner = Math.sqrt(Math.pow(midX, 2) + Math.pow(midY, 2));

        double distToMidPoint = Math.sqrt(Math.pow(midX - point.x, 2) + Math.pow(midY - point.y, 2));
        double normDistToMidPoint = distToMidPoint/ distFromMidToCorner;
        double invNormDistToMidPoint = 1.0 - normDistToMidPoint;

        point.z = invNormDistToMidPoint;
    }

}
