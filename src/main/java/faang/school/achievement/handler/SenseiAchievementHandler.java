package faang.school.achievement.handler;

import faang.school.achievement.dto.MentorshipEvent;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SenseiAchievementHandler extends AbstractEventHandler<MentorshipEvent> {

    @Value("${achievements.title.sensei}")
    private String achievementName;

    public SenseiAchievementHandler(AchievementService achievementService,
                                    UserAchievementRepository userAchievementRepository,
                                    AchievementProgressRepository achievementProgressRepository){
        super(achievementService, userAchievementRepository, achievementProgressRepository);
    }

    @Override
    protected String getAchievementName(){
        return achievementName;
    }

}
