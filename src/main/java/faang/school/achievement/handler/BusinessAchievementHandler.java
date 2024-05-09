package faang.school.achievement.handler;

import faang.school.achievement.dto.ProjectEvent;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BusinessAchievementHandler extends AbstractEventHandler<ProjectEvent> {

    @Value("${achievements.title.businessman}")
    private String businessman;

    public BusinessAchievementHandler(AchievementService achievementService, UserAchievementRepository userAchievementRepository, AchievementProgressRepository achievementProgressRepository) {
        super(achievementService, userAchievementRepository, achievementProgressRepository);
    }

    @Override
    protected boolean isSupportedEventType(ProjectEvent event) {
        return true;
    }

    @Override
    protected String getAchievementName() {
        return businessman;
    }
}


