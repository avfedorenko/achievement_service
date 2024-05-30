package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MentorshipEventListener extends AbstractListener<MentorshipEvent> {

    public MentorshipEventListener(ObjectMapper objectMapper,
                                   List<EventHandler<MentorshipEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected MentorshipEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), MentorshipEvent.class);

    }
}
