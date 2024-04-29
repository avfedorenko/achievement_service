package faang.school.achievement.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MentorshipEventListener extends AbstractListener<MentorshipStartEvent>{
    public MentorshipEventListener(ObjectMapper objectMapper, AchievementService achievementService){
        super(objectMapper, achievementService);
    }

    @Override
    protected MentorshipStartEvent listenEvent(Message message) throws IOException{
        return objectMapper.readValue(message.getBody(), MentorshipStartEvent.class);

    }
}
