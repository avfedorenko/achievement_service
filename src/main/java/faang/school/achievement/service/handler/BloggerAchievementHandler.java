package faang.school.achievement.service.handler;

import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;

public class BloggerAchievementHandler extends AbstractEventHandler<FollowerEvent> {
    @Value("${achievements.bloger}")
    private String achievementName;

    public BloggerAchievementHandler(AchievementService achievementService,
                                     UserAchievementRepository userAchievementRepository,
                                     AchievementProgressRepository achievementProgressRepository){
        super(achievementService, userAchievementRepository, achievementProgressRepository);
    }

    @Override
    protected String getSupportedEventType() {
        return "FollowerEvent";
    }

    @Override
    public void handleEvent(FollowerEvent event) {
        handleEvent(event);
    }

    @Override
    protected String getAchievementName() {
        return achievementName;
    }
}
