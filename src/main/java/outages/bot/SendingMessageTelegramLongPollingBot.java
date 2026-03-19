package outages.bot;

import org.telegram.telegrambots.meta.generics.LongPollingBot;
import outages.pojo.Outage;

public interface SendingMessageTelegramLongPollingBot extends LongPollingBot {

    boolean sendMessage(Outage outage, Long chatIds);
}
