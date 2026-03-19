package outages.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {
    private String balloonContentHeader;
    private String balloonContentBody;
    private String balloonContentFooter;
    private String hintContent;
}