package outages.outage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import outages.bot.SendingMessageTelegramLongPollingBot;
import outages.pojo.Outage;
import outages.repository.SentNotificationRepository;
import outages.service.SentNotificationService;

import java.util.UUID;

@Component
public class ProcessOutageMy implements ProcessOutage {

    @Autowired
    SendingMessageTelegramLongPollingBot bot;

    @Autowired
    SentNotificationService service;

    @Autowired
    SentNotificationRepository snr;

    @Value("${bot.chatIds}")
    private Long[] chatIds;

    @Override
    public void process(Outage outage) {
        UUID outageId = outage.getId();
        for (Long chatId : chatIds) {
            if (!snr.existsByIdChatIdAndIdOutageId(chatId, outageId)) {
                if (bot.sendMessage(outage, chatId)) {
                    service.markAsSent(chatId, outage.getId());
                };
            }
        }
    }
}
