package outages.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {
    List<Float> coordinates;

    public Float getX() {
        return coordinates.get(0);
    }

    public Float getY() {
        return coordinates.get(1);
    }
}
