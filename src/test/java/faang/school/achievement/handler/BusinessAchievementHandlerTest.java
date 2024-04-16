package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.exceptions.AchievementNotFoundException;
import faang.school.achievement.exceptions.MessageError;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.services.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessAchievementHandlerTest {
    @Mock
    AchievementCache achievementCache;
    @Mock
    AchievementService achievementService;
    private final static String TITLE = "BUSINESSMEN";
    @InjectMocks
    BusinessAchievementHandler businessAchievementHandler;
    long userId = 1L;

    Achievement firstAchievement;
    AchievementProgress firstProgress;
    AchievementProgress secondProgress;

    @BeforeEach
    void setUp() {
        firstAchievement = Achievement.builder()
                .id(1L)
                .title(TITLE)
                .points(3)
                .build();
        firstProgress = AchievementProgress.builder()
                .id(1L)
                .currentPoints(2)
                .achievement(firstAchievement)
                .build();
        secondProgress = AchievementProgress.builder()
                .id(1L)
                .currentPoints(1)
                .achievement(firstAchievement)
                .build();
    }

    @Test
    void testHandle_AchievementNotFoundInCache() {
        when(achievementCache.getAchievement(TITLE)).thenReturn(Optional.empty());

        AchievementNotFoundException e = assertThrows(AchievementNotFoundException.class, () -> businessAchievementHandler.handle(userId));
        assertEquals(MessageError.ACHIEVEMENT_NOT_FOUND_IN_CACHE.getMessage(), e.getMessage());
    }

    @Test
    void testHandle_AchievementAlreadyGiven() {
        when(achievementCache.getAchievement(TITLE)).thenReturn(Optional.ofNullable(firstAchievement));
        when(achievementService.hasAchievement(userId, firstAchievement.getId())).thenReturn(true);

        businessAchievementHandler.handle(userId);
    }

    @Test
    void testHandle_NotEnoughPoints() {
        when(achievementCache.getAchievement(TITLE)).thenReturn(Optional.ofNullable(firstAchievement));
        when(achievementService.hasAchievement(userId, firstAchievement.getId())).thenReturn(false);
        when(achievementService.getProgress(userId, firstAchievement)).thenReturn(secondProgress);

        businessAchievementHandler.handle(userId);
    }


    @Test
    void testHandle_EnoughPoints() {
        when(achievementCache.getAchievement(TITLE)).thenReturn(Optional.ofNullable(firstAchievement));
        when(achievementService.hasAchievement(userId, firstAchievement.getId())).thenReturn(false);
        when(achievementService.getProgress(userId, firstAchievement)).thenReturn(firstProgress);

        businessAchievementHandler.handle(userId);

        verify(achievementService, times(1)).giveAchievement(userId, firstAchievement);
    }
}