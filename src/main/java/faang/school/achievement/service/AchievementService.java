package faang.school.achievement.service;

import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService{

    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository progressRepository;
    public boolean hasAchievement(long userId, long achievementId){
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public void createProgressIfNecessary(long userId, long achievementId){
        progressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public Optional<AchievementProgress> getProgress(long userId, long achievementId){
        return progressRepository.findByUserIdAndAchievementId(userId, achievementId );
    }

    public void giveAchievement(UserAchievement userAchievement){

        userAchievementRepository.save(userAchievement);

    }
}
