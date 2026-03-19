package outages.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;


@Data
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

    public static class Builder {
        private String type = "Feature";
        private UUID id;
        private Properties properties = new Properties();
        private Geometry geometry = new Geometry();

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder geometry(Geometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public Outage build() {
            Outage outage = new Outage();
            outage.setId(id);
            outage.setType(type);
            outage.setProperties(properties);
            outage.setGeometry(geometry);
            return outage;
        }
    }
}