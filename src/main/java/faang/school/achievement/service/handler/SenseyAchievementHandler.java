package faang.school.achievement.service.handler;

import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

public class SenseyAchievementHandler extends AbstractEventHandler<MentorshipStartEvent>{

    @Value("${achievements.sensey}")
    private String achievementName;

    public SenseyAchievementHandler(AchievementService achievementService,
                                    UserAchievementRepository userAchievementRepository,
                                    AchievementProgressRepository achievementProgressRepository){
        super(achievementService, userAchievementRepository, achievementProgressRepository);
    }

    @Override
    protected String getSupportedEventType(){
        return "MentorshipStartEvent";
    }

    @Override
    protected String getAchievementName(){
        return achievementName;
    }

    @Async
    @Retryable(value = {OptimisticLockingFailureException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void handleEventAsync(MentorshipStartEvent event) {
        handleEvent(event);
    }
}
