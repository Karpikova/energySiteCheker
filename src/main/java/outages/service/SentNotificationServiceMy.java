package outages.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import outages.html.BodyHtml;
import outages.model.SentNotification;
import outages.model.SentNotificationId;
import outages.repository.SentNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SentNotificationServiceMy implements SentNotificationService {

    private final static Logger LOGGER = LogManager.getLogger(SentNotificationServiceMy.class);

    @Autowired
    private SentNotificationRepository repository;

    @Override
    public void markAsSent(Long chatId, UUID outageId) {
        SentNotificationId id = new SentNotificationId(chatId, outageId);
        SentNotification notification = new SentNotification();
        notification.setId(id);
        repository.save(notification);
        LOGGER.info("Written to db {} (chatId), {} (outageId).", chatId, outageId);
    }

    @Override
    public boolean existsByIdChatIdAndIdOutageId(Long chatId, UUID outageId) {
        return repository.existsByIdChatIdAndIdOutageId(chatId, outageId);
    }
}
