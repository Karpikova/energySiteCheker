package outages.outage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import outages.bot.SendingMessageTelegramLongPollingBot;
import outages.pojo.Outage;

@Component
public class ProcessOutageMy implements ProcessOutage {

    @Autowired
    SendingMessageTelegramLongPollingBot bot;

    @Value("${bot.chatIds}")
    private Long[] chatIds;

    @Override
    public void process(Outage outage) {
        bot.sendMessage(outage.printableView(), chatIds);
    }
}
