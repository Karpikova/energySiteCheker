package outages.sceduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import outages.outage.OutageService;

@Component
public final class ScheduledCheckEnergy implements ScheduledCheck {

    @Autowired
    OutageService os;

    @Value("${bot.chatIds}")
    private Long[] chatIds;

    @Override
    @Scheduled(fixedRateString = "${bot.scheduler.interval}")
    public void scheduledCheck() {
        os.checkNearBy(chatIds);
    }

    @Scheduled(fixedRateString = "${bot.scheduler.intervalCommon}")
    @Override
    public void scheduledCheckCommon() {
        os.checkSizeAround(chatIds);
    }
}
