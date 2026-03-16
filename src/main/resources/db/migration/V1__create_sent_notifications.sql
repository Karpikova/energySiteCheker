CREATE TABLE sent_notifications (
    chat_id BIGINT NOT NULL,
    outage_id UUID NOT NULL,
    sent_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (chat_id, outage_id)
);