package outages.service;

import org.springframework.beans.factory.annotation.Autowired;
import outages.model.SentNotification;
import outages.model.SentNotificationId;
import outages.repository.SentNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SentNotificationServiceMy implements SentNotificationService {

    @Autowired
    private SentNotificationRepository repository;

    @Override
    public void markAsSent(Long chatId, UUID outageId) {
        SentNotificationId id = new SentNotificationId(chatId, outageId);
        SentNotification notification = new SentNotification();
        notification.setId(id);
        repository.save(notification);
    }

    @Override
    public boolean existsByIdChatIdAndIdOutageId(Long chatId, UUID outageId) {
        repository.existsByIdChatIdAndIdOutageId(chatId, outageId);
        return false;
    }
}
