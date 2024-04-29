package faang.school.achievement.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public abstract class AbstractListener<T> implements MessageListener{

    protected final ObjectMapper objectMapper;
    private final AchievementService achievementService;


    public AbstractListener(ObjectMapper objectMapper, AchievementService achievementService) {
        this.objectMapper = objectMapper;
        this.achievementService=achievementService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        T event;
        try {
            event = listenEvent(message);
        } catch (IOException e) {
            log.warn("Unsuccessful mapping", e);
            throw new RuntimeException(e);
        }

        log.info("Data successfully passed to analyticsEventService");
    }

    protected abstract T listenEvent(Message message) throws IOException;

}