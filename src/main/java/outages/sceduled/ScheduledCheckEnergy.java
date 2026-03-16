package outages.sceduled;

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
    public void scheduledCheck() throws Exception {
        String body = new BodyHtml(HttpClient.newHttpClient(), baseUrl).body();
        FilteredOutages filtered = new FilteredOutagesMy(new FoundOutagesFromWeb(body).outages());
        List<Outage> filteredByText = filtered.filteredByStringInHeader(textToFind);
        sendOutage(filteredByText);
        List<Outage> filteredByRadius = filtered.filteredByRadius(x, y, r);
        sendOutage(filteredByRadius);
    }

    private void sendOutage(List<Outage> outages) {
        for (Outage outage : outages) {
            processOutage.process(outage);
        }
    }
}
