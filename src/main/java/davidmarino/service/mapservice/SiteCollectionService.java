package davidmarino.service.mapservice;

import davidmarino.model.mapmodels.Polygon;
import davidmarino.model.mapmodels.Site;
import davidmarino.model.mapmodels.SiteCollection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class SiteCollectionService {
    public SiteCollection getSites(ArrayList<Polygon> polygons, double waterLevel, int numberOfDungeons, int width, int height) {
        SiteCollection siteCollection = new SiteCollection();
        ArrayList<Polygon> queryList = new ArrayList<>();
        for (Polygon polygon : polygons) {
            if (polygon.site.z > waterLevel) {
                queryList.add(polygon);
            }
        }
        Random random = new Random();

        for (int i = 0; i < numberOfDungeons; i++) {
            int j = random.nextInt(0, queryList.size());
            Polygon polygon = queryList.get(j);
            Site site = new Site("dungeonSiteName: " + j, (int) (polygon.site.x / (width / 500)), (int) (polygon.site.y / (height / 500)));
            siteCollection.sites.add(site);
        }
        return siteCollection;
    }


}
