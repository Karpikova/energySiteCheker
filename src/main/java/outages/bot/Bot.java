package outages.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import outages.pojo.Outage;

@Component
public final class Bot extends TelegramLongPollingBot implements SendingMessageTelegramLongPollingBot {

    private final static Logger LOGGER = LogManager.getLogger(Bot.class);

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.url}")
    private String baseUrl;

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    @Override
    public boolean sendMessage(Outage outage, Long chatId) {
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText(outage.printableView());
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
            System.out.println(update.getUpdateId());
            SendMessage response = new SendMessage();
            response.setChatId(String.valueOf(update.getUpdateId()));
            response.setText("I'm alive");
            try {
                execute(response);
            } catch (TelegramApiException e) {
                LOGGER.error("Failed to send message: {}", e);
            }
        }
    }

}