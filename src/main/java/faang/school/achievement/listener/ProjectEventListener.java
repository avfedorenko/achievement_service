package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProjectEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ProjectEventListener extends AbstractListener<ProjectEvent> {
    public ProjectEventListener(List<EventHandler<ProjectEvent>> eventHandlers,
                                ObjectMapper objectMapper) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected ProjectEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), ProjectEvent.class);

    }
}

