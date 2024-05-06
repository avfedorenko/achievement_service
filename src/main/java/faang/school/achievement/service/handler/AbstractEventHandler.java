package faang.school.achievement.service.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.dto.event.Event;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public abstract class AbstractEventHandler<T extends Event> implements EventHandler<T>{

    protected final AchievementService achievementService;
    protected final UserAchievementRepository userAchievementRepository;
    protected final AchievementProgressRepository achievementProgressRepository;

    public AbstractEventHandler(AchievementService achievementService,
                                UserAchievementRepository userAchievementRepository,
                                AchievementProgressRepository progressRepository
    ){
        this.achievementService=achievementService;
        this.userAchievementRepository=userAchievementRepository;
        this.achievementProgressRepository=progressRepository;

    }

    @Override
    public boolean canHandle(T event) {
        // Delegate the decision to subclasses
        return isSupportedEventType(event);
    }

    // Abstract method for subclasses to implement
    protected abstract boolean isSupportedEventType(T event);

    @Override
    @Async
    public void handleEvent(T event){
        Achievement achievement = null;

        try{
            achievement=achievementService.getAchievementByName(getAchievementName());
        }catch(EntityNotFoundException ex){
            log.warn(ex.getMessage());
        }

        long userId=event.getUserId();
        long achievementId = achievement.getId();

        if(achievementService.hasAchievement(userId, achievementId)){
            log.info("User has already received achievement: {}", getAchievementName());
            return;
        }

        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress progress = achievementService.getProgress(userId, achievementId).get();
        achievementService.incrementAchievementProgress(progress);
        achievementService.updateAchievementProgress(progress);
        giveAchievementIfEnoughScore(progress, achievement);

    }

    protected abstract String getAchievementName();

    protected void giveAchievementIfEnoughScore(AchievementProgress progress, Achievement achievement){
        if(progress.getCurrentPoints()>=achievement.getPoints()){
            UserAchievement userAchievement=UserAchievement
                    .builder().userId(progress.getUserId())
                    .achievement(achievement).build();
            achievementService.giveAchievement(userAchievement);
        }
    }
}
