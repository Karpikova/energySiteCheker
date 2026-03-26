package outages.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public final class SentNotificationId implements Serializable {

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "outage_id")
    private UUID outageId;
}