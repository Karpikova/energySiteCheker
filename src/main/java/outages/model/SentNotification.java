package outages.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sent_notifications")
public final class SentNotification {

    @EmbeddedId
    private SentNotificationId id;

    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();
}

