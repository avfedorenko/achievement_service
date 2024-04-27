package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.services.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BusinessAchievementHandler extends AbstractAchievementHandler {

    private final static String TITLE = "BUSINESSMEN";

    public BusinessAchievementHandler(AchievementCache achievementCache,
                                      AchievementService achievementService) {
        super(achievementService, achievementCache, TITLE);
    }
}