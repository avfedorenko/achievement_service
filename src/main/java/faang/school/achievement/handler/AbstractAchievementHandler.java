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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractAchievementHandler {
    private final AchievementService achievementService;
    private final AchievementCache achievementCache;
    private final String title;

    @Async("executor")
    @Transactional
    public void handle(long userId) {
        log.info("Started handling");
        Achievement achievement = achievementCache.getAchievement(title)
                .orElseThrow(() -> new AchievementNotFoundException(MessageError.ACHIEVEMENT_NOT_FOUND_IN_CACHE));
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

