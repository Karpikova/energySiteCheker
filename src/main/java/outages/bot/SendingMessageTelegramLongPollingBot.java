package outages.bot;

import org.telegram.telegrambots.meta.generics.LongPollingBot;
import outages.pojo.Outage;

public interface SendingMessageTelegramLongPollingBot extends LongPollingBot {

    void sendMessage(Outage outage, Long... chatIds);
}
