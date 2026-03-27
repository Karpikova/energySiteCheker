package outages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import outages.model.SentNotification;
import outages.model.SentNotificationId;

import java.util.UUID;

@Repository
public interface SentNotificationRepository extends JpaRepository<SentNotification, SentNotificationId> {

    //Всё должно работать и без query, но возникла проблема скорее всего из-за сочентания версий либ
    //и использования UUID. Решила сделать так.
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM SentNotification s WHERE s.id.chatId = :chatId AND s.id.outageId = :outageId")
    boolean existsByIdChatIdAndIdOutageId(@Param("chatId") Long chatId, @Param("outageId") UUID outageId);
}