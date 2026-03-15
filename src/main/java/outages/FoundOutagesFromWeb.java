package outages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import outages.html.BodyHtml;
import outages.pojo.Outage;

import java.net.http.HttpClient;
import java.util.List;

public final class FoundOutagesFromWeb implements FoundOutages {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String body;

    public FoundOutagesFromWeb(String body) {
        this.body = body;
    }

    @Override
    public List<Outage> outages() throws Exception {
        List<Outage> outages = mapper.readValue(body, new TypeReference<>() {
        });
        return outages;
    }
}
