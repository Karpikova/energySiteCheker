package outages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import outages.model.SentNotification;
import outages.model.SentNotificationId;

import java.util.UUID;

@Repository
public interface SentNotificationRepository extends JpaRepository<SentNotification, SentNotificationId> {
    boolean existsByIdChatIdAndIdOutageId(Long chatId, UUID outageId);
}