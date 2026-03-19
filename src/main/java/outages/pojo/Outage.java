package outages.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Outage {
    private String type;
    private UUID id;
    private Properties properties;
    private Geometry geometry;

    public String getBalloonContentHeader() {
        return properties.getBalloonContentHeader();
    }

    public String printableView() {
        return type + System.lineSeparator() +
                getId() + System.lineSeparator() +
                properties.getBalloonContentHeader() + System.lineSeparator() +
                properties.getBalloonContentBody() + System.lineSeparator() +
                properties.getBalloonContentFooter() + System.lineSeparator();
    }
}