package faang.school.achievement.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProjectEvent;
import faang.school.achievement.handler.BusinessAchievementHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProjectEventListener extends AbstractListener<ProjectEvent> implements MessageListener {
    public ProjectEventListener(BusinessAchievementHandler businessAchievementHandler,
                                ObjectMapper objectMapper) {
        super(businessAchievementHandler, objectMapper);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleMessage(message, ProjectEvent.class);
    }
}
