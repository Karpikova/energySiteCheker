package outages.outage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import outages.html.BodyHtml;
import outages.pojo.Outage;

import java.net.http.HttpClient;
import java.util.List;

@Component
public class OutageServiceMy implements OutageService {

    private final static Logger LOGGER = LogManager.getLogger(OutageServiceMy.class);

    @Autowired
    ProcessOutage processOutage;

    @Value("${bot.url}")
    private String baseUrl;

    @Value("${bot.textToFind}")
    private String textToFind;

    @Value("${bot.x}")
    private Float x;

    @Value("${bot.y}")
    private Float y;

    @Value("${bot.r}")
    private Float r;

    @Override
    public void check(Long[] chatIds) {
        try {
            String body = new BodyHtml(HttpClient.newHttpClient(), baseUrl).body();
            FilteredOutages filtered = new FilteredOutagesMy(new FoundOutagesFromWeb(body).outages());
            List<Outage> filteredByText = filtered.filteredByStringInHeader(textToFind);
            LOGGER.info("Found {} outages by text {}.", filteredByText.size(), textToFind);
            sendOutage(filteredByText);
            List<Outage> filteredByRadius = filtered.filteredByRadius(x, y, r);
            LOGGER.info("Found {} outages by radius {}.", filteredByRadius.size(), r);
            sendOutage(filteredByRadius);
        } catch (Exception e) {
            LOGGER.error("Ошибка верхнего уровня: {}", e);
        }
    }

    private void sendOutage(List<Outage> outages) {
        for (Outage outage : outages) {
            processOutage.process(outage);
        }
    }
}
