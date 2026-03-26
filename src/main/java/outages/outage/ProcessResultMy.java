package outages.outage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import outages.bot.SendingMessageTelegramLongPollingBot;
import outages.pojo.Outage;
import outages.service.SentNotificationService;

import java.util.UUID;

@Component
public final class ProcessResultMy implements ProcessResult {

    @Autowired
    @Lazy
    SendingMessageTelegramLongPollingBot bot;

    @Autowired
    SentNotificationService service;

    @Value("${bot.chatIds}")
    private Long[] chatIds;

    @Override
    public void processOutage(Outage outage) {
        UUID outageId = outage.getId();
        for (Long chatId : chatIds) {
            if (!service.existsByIdChatIdAndIdOutageId(chatId, outageId)) {
                if (bot.sendMessage(outage, chatId)) {
                    service.markAsSent(chatId, outage.getId());
                }
            }
        }
    }

    @Override
    public void processPlainText(String text) {
        for (Long chatId : chatIds) {
            bot.sendMessage(text, chatId);
        }
    }
}
