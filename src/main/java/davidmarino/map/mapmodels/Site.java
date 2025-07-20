package davidmarino.map.mapmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class Site {
    @JsonProperty("dungeon_site_name")
    public String dungeonSiteName;
    @JsonProperty("dungeonSiteX")
    public Integer x;
    @JsonProperty("dungeonSiteY")
    public Integer y;

    public Site(String dungeonSiteName, Integer x, Integer y) {
        this.dungeonSiteName = dungeonSiteName;
        this.x = x;
        this.y = y;
    }

    public Site() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Site [dungeon_site_name=");
        builder.append(dungeonSiteName);
        builder.append(", x=");
        builder.append(x);
        builder.append(", y=");
        builder.append(y);
        builder.append("]");
        return builder.toString();
    }
}
