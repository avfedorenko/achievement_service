package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.LikeEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LikeEventListener extends AbstractListener<LikeEvent> {

    public LikeEventListener(ObjectMapper objectMapper,
                             List<EventHandler<LikeEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected LikeEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), LikeEvent.class);
    }
}