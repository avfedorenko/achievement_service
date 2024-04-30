package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementService{

    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository progressRepository;
    private final AchievementCache achievementCache;
    public boolean hasAchievement(long userId, long achievementId){
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId){
        progressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public Optional<AchievementProgress> getProgress(long userId, long achievementId){
        return progressRepository.findByUserIdAndAchievementId(userId, achievementId );
    }

    @Transactional
    public void giveAchievement(UserAchievement userAchievement){

        userAchievementRepository.save(userAchievement);

    }

    public Achievement getAchievementByName(String name){
        Optional<Achievement> achievement = achievementCache.getAchievement(name);
        if( achievement.isPresent()){
            return achievement.get();
        }else{
            throw new EntityNotFoundException("No achievement with name " + name);
        }
    }

    @Transactional
    public void updateAchievementProgress(AchievementProgress achievementProgress) {
         progressRepository.save(achievementProgress);
    }

    public void incrementAchievementProgress(AchievementProgress achievementProgress){
        AtomicLong currentPoints = new AtomicLong(achievementProgress.getCurrentPoints());
        currentPoints.incrementAndGet();
        achievementProgress.setCurrentPoints(currentPoints.get());
}
