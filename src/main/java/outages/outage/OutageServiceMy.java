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
    ProcessResult processResult;

    @Value("${bot.url}")
    private String baseUrl;

    @Value("${bot.pointSearch}")
    private String pointSearch;

    @Value("${bot.commonSearch}")
    private String[] commonSearch;

    @Value("${bot.x}")
    private Float x;

    @Value("${bot.y}")
    private Float y;

    @Value("${bot.r}")
    private Float r;

    @Override
    public void checkNearBy(Long[] chatIds) {
        try {
            String body = new BodyHtml(HttpClient.newHttpClient(), baseUrl).body();
            FilteredOutages filtered = new FilteredOutagesMy(new FoundOutagesFromWeb(body).outages());
            List<Outage> filteredByText = filtered.filteredByStringInHeader(pointSearch);
            LOGGER.info("Found {} outages by text {}.", filteredByText.size(), pointSearch);
            sendOutage(filteredByText);
            List<Outage> filteredByRadius = filtered.filteredByRadius(x, y, r);
            LOGGER.info("Found {} outages by radius {}.", filteredByRadius.size(), r);
            sendOutage(filteredByRadius);
        } catch (Exception e) {
            LOGGER.error("Ошибка верхнего уровня: {}", e);
        }
    }

    @Override
    public void checkSizeAround(Long[] chatIds) {
        try {
            String body = new BodyHtml(HttpClient.newHttpClient(), baseUrl).body();
            for (String search : commonSearch) {
                FilteredOutages filtered = new FilteredOutagesMy(new FoundOutagesFromWeb(body).outages());
                int filteredByTextSize = filtered.filteredByStringInHeader(search).size();
                LOGGER.info("Found {} outages by text {}.", filteredByTextSize, search);
                processResult.processPlainText(String.format("Отключений в %s %s", search, filteredByTextSize));
            }
        } catch (Exception e) {
            LOGGER.error("Ошибка верхнего уровня: {}", e);
        }
    }

    private void sendOutage(List<Outage> outages) {
        for (Outage outage : outages) {
            processResult.processOutage(outage);
        }
    }
}
