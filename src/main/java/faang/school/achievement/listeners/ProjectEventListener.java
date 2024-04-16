package faang.school.achievement.listeners;

import faang.school.achievement.handler.BusinessAchievementHandler;
import faang.school.achievement.dto.ProjectEvent;
import faang.school.achievement.mappers.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectEventListener implements MessageListener {

    private final JsonMapper jsonMapper;
    private final BusinessAchievementHandler businessAchievementHandler;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Message arrived {}", Thread.currentThread());
        ProjectEvent event = jsonMapper.toObject(message.getBody(), ProjectEvent.class);
        long userId = event.getAuthorId();
        businessAchievementHandler.handle(userId);
    }
}
