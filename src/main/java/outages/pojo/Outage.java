package outages.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Outage {
    private String type;
    private String id;
    private Properties properties;
    private Geometry geometry;

    public String getBalloonContentHeader() {
        return properties.getBalloonContentHeader();
    }

    public String printableView() {
        return type + System.lineSeparator() +
                properties.getBalloonContentHeader() + System.lineSeparator() +
                properties.getBalloonContentBody() + System.lineSeparator() +
                properties.getBalloonContentFooter() + System.lineSeparator();
    }
}