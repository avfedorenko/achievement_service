package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.exceptions.AchievementNotFoundException;
import faang.school.achievement.exceptions.MessageError;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.services.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class BusinessAchievementHandler {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    private final static String TITLE = "BUSINESSMEN";

    @Async("executor")
    @Transactional
    public void handle(long userId) {
        log.info("Started handling");
        Achievement achievement = achievementCache.getAchievement(TITLE)//why are we taking this achievement from BD if we can just
                .orElseThrow(() -> new AchievementNotFoundException(MessageError.ACHIEVEMENT_NOT_FOUND_IN_CACHE));//consider take it away in another method or class
        if (achievementService.hasAchievement(userId, achievement.getId())) {
            return;
        }

        AchievementProgress progress = achievementService.getProgress(userId, achievement);
        progress.increment();

        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement);
        }

        log.info("Handled successfully {}", Thread.currentThread());
    }
}