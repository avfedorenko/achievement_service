package faang.school.achievement.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.Event;
import faang.school.achievement.handler.AbstractAchievementHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public abstract class AbstractListener<T extends Event> {

    private final AbstractAchievementHandler abstractAchievementHandler;
    private final ObjectMapper objectMapper;

    public void handleMessage(Message message, Class<T> type) {
        log.info("Message arrived {}", Thread.currentThread());
        T event;
        try {
            event = objectMapper.readValue(message.getBody(), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long userId = event.getAchievementHolderId();
        abstractAchievementHandler.handle(userId);
    }
}
