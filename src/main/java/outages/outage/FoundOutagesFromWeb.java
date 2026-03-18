package outages.outage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import outages.pojo.Outage;

import java.util.List;

public final class FoundOutagesFromWeb implements FoundOutages {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String body;

    public FoundOutagesFromWeb(String body) {
        this.body = body;
    }

    @Override
    public List<Outage> outages() throws JsonProcessingException {
        return mapper.readValue(body, new TypeReference<>() {
        });
    }
}
