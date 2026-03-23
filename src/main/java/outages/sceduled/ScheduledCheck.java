package outages.sceduled;

import org.springframework.scheduling.annotation.Scheduled;

public interface ScheduledCheck {
    void scheduledCheck() throws Exception;

    @Scheduled(cron = "${bot.scheduler.cron}")
    void scheduledCheckCommon();
}
