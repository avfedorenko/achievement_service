package faang.school.achievement.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.Event;
import faang.school.achievement.service.handler.EventHandler;
import faang.school.achievement.service.handler.SenseyAchievementHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public abstract class AbstractListener<T extends Event> implements MessageListener{

    protected final ObjectMapper objectMapper;
    private final List<EventHandler<T>> eventHandlers;
    public AbstractListener(ObjectMapper objectMapper, List<EventHandler<T>> eventHandlers) {
        this.objectMapper = objectMapper;
        this.eventHandlers = eventHandlers;

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

        List<EventHandler<T>> filteredHandlers = eventHandlers.stream()
                .filter(handler->handler
                        .canHandle(event)).toList();
        log.info(filteredHandlers.toString());
        filteredHandlers.forEach(handler->handler.handleEvent(event));
        log.info("Data successfully passed to AchievementService!");
    }

    protected abstract T listenEvent(Message message) throws IOException;

}