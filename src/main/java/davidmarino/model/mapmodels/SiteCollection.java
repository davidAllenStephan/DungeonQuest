package davidmarino.model.mapmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SiteCollection {
    @JsonProperty("sites")
    public ArrayList<Site> sites;

    public SiteCollection() {
        sites = new ArrayList<>();
    }

    public SiteCollection(ArrayList<Site> sites) {
        this.sites = sites;
    }
}
