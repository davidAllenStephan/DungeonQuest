package davidmarino.model.mapmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import davidmarino.model.Parameters;
import davidmarino.service.mapservice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
public class Map {
    @JsonProperty("sites")
    public transient ArrayList<String> sites;
    @JsonProperty("map_image")
    public String mapImage;

    @Autowired
    private MapService mapService;

    public Map(Parameters parameters) {
        mapService = new MapService(parameters);
        mapImage = mapService.getBase64();
    }
}
