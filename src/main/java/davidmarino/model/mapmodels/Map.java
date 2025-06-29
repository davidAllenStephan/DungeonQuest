package davidmarino.model.mapmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import davidmarino.model.Parameters;
import davidmarino.service.mapservice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Map {
    @JsonProperty("sites")
    public transient ArrayList<String> sites;
    @JsonProperty("map_image")
    public String mapImage;

    @Autowired
    private transient MapService mapService;

    public Map(Parameters parameters) {
        mapService = new MapService(parameters);
        mapImage = mapService.getBase64();
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
