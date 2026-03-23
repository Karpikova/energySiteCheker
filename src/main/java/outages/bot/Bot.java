package outages.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import outages.outage.OutageService;
import outages.pojo.Outage;

@Component
public final class Bot extends TelegramLongPollingBot implements SendingMessageTelegramLongPollingBot {

    private final static Logger LOGGER = LogManager.getLogger(Bot.class);

    @Autowired
    OutageService os;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    @Override
    public boolean sendMessage(Outage outage, Long chatId) {
        return sendMessage(outage.printableView(), chatId);
    }

    @Override
    public boolean sendMessage(String text, Long chatId) {
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText(text);
        try {
            System.out.println(chatId);
            execute(response);
            return true;
        } catch (TelegramApiException e) {
            LOGGER.error("Failed to send message: {}", e);
            return false;
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long[] chatId = {update.getMessage().getChatId()};
            os.checkNearBy(chatId);
        }
    }

}