package outages.sceduled;

import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import outages.html.BodyHtml;
import outages.outage.FilteredOutages;
import outages.outage.FilteredOutagesMy;
import outages.outage.FoundOutagesFromWeb;
import outages.outage.ProcessOutage;
import outages.pojo.Outage;

import java.net.http.HttpClient;
import java.util.List;

@Component
public final class ScheduledCheckEnergy implements ScheduledCheck {

    private final static Logger LOGGER = LogManager.getLogger(ScheduledCheckEnergy.class);

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
    @Scheduled(fixedRateString = "${bot.scheduler.interval}")
    public void scheduledCheck(){
        try {
            String body = new BodyHtml(HttpClient.newHttpClient(), baseUrl).body();
        FilteredOutages filtered = new FilteredOutagesMy(new FoundOutagesFromWeb(body).outages());
        List<Outage> filteredByText = filtered.filteredByStringInHeader(textToFind);
        sendOutage(filteredByText);
        List<Outage> filteredByRadius = filtered.filteredByRadius(x, y, r);
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
