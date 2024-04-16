package faang.school.achievement.services;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@EnableAsync
public class AchievementService {
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;


    @Transactional
    public AchievementProgress getProgress(long userId, Achievement achievement) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievement.getId())
                .orElseGet(() -> saveProgressWithUserIdAndAchievement(userId, achievement));
    }

    public boolean hasAchievement(Long userId, Long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void giveAchievement(long userId, Achievement achievement) {
        userAchievementRepository.save(
                UserAchievement.builder()
                        .achievement(achievement)
                        .userId(userId)
                        .build()
        );
    }

    private AchievementProgress saveProgressWithUserIdAndAchievement(long userId, Achievement achievement) {
        return achievementProgressRepository.save(AchievementProgress.builder()
                .userId(userId)
                .achievement(achievement)
                .build()
        );
    }
}
