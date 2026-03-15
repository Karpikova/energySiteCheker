package outages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import outages.bot.SendingMessageTelegramLongPollingBot;
import outages.html.BodyHtml;
import outages.pojo.Outage;

import java.net.http.HttpClient;
import java.util.List;

@Component
public final class ScheduledCheckEnergy implements ScheduledCheck {

    @Value("${bot.url}")
    private String baseUrl;

    @Value("${bot.textToFind}")
    private String textToFind;

    @Autowired
    SendingMessageTelegramLongPollingBot bot;

    @Value("${bot.chatId}")
    private Long[] chatId;

    @Override
    @Scheduled(fixedRateString = "${bot.scheduler.interval}")
    public void scheduledCheck() throws Exception {
        String body = new BodyHtml(HttpClient.newHttpClient(), baseUrl).body();
        FilteredOutages filtered = new FilteredOutagesMy(new FoundOutagesFromWeb(body).outages());
        List<Outage> filteredByText = filtered.filteredByStringInHeader(textToFind);
        sendOutage(filteredByText);
        List<Outage> filteredByRadius = filtered.filteredByRadius(52.435746f, 104.388898f, 0.003f);
        sendOutage(filteredByRadius);
    }

    private void sendOutage(List<Outage> outages) {
        outages.forEach(outage -> bot.sendMessage(outage.printableView(), chatId));
    }
}
