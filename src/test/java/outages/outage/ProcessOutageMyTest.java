package outages.outage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import outages.bot.SendingMessageTelegramLongPollingBot;
import outages.model.SentNotification;
import outages.model.SentNotificationId;
import outages.pojo.Geometry;
import outages.pojo.Outage;
import outages.pojo.Properties;
import outages.repository.SentNotificationRepository;
import outages.sceduled.ScheduledCheck;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class ProcessOutageMyTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProcessOutage processOutage;

    @Autowired
    SentNotificationRepository repository;

    @PersistenceContext
    private EntityManager em;

    @MockBean
    private ScheduledCheck sc;

    @MockBean
    private SendingMessageTelegramLongPollingBot bot;

    @Value("${bot.myChatId}")
    private Long myChatId;

    @Value("${bot.husbandsChatId}")
    private Long husbandsChatId;

    UUID uuid1 = UUID.fromString("550e0000-0000-0000-0000-000000000000");
    UUID uuid2 = UUID.fromString("660e0000-0000-0000-0000-000000000000");

    @BeforeEach
    public void setUp() {
        Mockito.when(bot.sendMessage(any(),any())).thenReturn(true);

        SentNotification notification1 = new SentNotification();
        notification1.setId(new SentNotificationId(myChatId, uuid1));
        repository.save(notification1);

        SentNotification notification2 = new SentNotification();
        notification2.setId(new SentNotificationId(myChatId, uuid2));
        repository.save(notification2);

        SentNotification notification3 = new SentNotification();
        notification3.setId(new SentNotificationId(husbandsChatId, uuid1));
        repository.save(notification3);

    }

    @Test
    public void processTest() {
        Outage outage1 = new Outage("Feature", uuid1, new Properties(), new Geometry());
        processOutage.process(outage1);
        Assert.assertEquals(3, repository.count());

        Outage outage2 = new Outage("Feature", uuid2, new Properties(), new Geometry());
        processOutage.process(outage2);
        Assert.assertEquals(4, repository.count());
        long countById = em.createQuery("SELECT COUNT(s) FROM SentNotification s WHERE s.id.chatId = :chatId",
                Long.class)
                .setParameter("chatId", husbandsChatId)
                .getSingleResult();
        Assert.assertEquals(2, countById);
    }
}