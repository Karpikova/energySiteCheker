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
import outages.pojo.Outage;
import outages.service.SentNotificationService;

@Component
public final class Bot extends TelegramLongPollingBot implements SendingMessageTelegramLongPollingBot {

    private final static Logger LOGGER = LogManager.getLogger(Bot.class);

    @Autowired
    SentNotificationService service;

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
    public void sendMessage(Outage outage, Long... chatIds) {
        SendMessage response = new SendMessage();
        for (Long chatId : chatIds) {
            response.setChatId(String.valueOf(chatId));
            response.setText(outage.printableView());
            try {
                System.out.println(chatId);
                execute(response);
                service.markAsSent(chatId, outage.getId());
            } catch (TelegramApiException e) {
                LOGGER.error("Failed to send message: {}", e);
            }
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
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