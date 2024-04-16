package faang.school.achievement.services;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    @Mock
    UserAchievementRepository userAchievementRepository;
    @Mock
    AchievementProgressRepository achievementProgressRepository;

    @InjectMocks
    AchievementService achievementService;

    long userId = 1L;
    Achievement firstAchievement;
    AchievementProgress firstAchievementProgress;
    AchievementProgress savedFirstAchievementProgress;
    UserAchievement firstUserAchievement;


    @BeforeEach
    void setUp() {
        firstAchievement = Achievement.builder()
                .id(1L)
                .title("BUSINESSMAN")
                .build();

        firstAchievementProgress = AchievementProgress.builder()
                .id(1L)
                .userId(userId)
                .achievement(firstAchievement)
                .build();

        savedFirstAchievementProgress = AchievementProgress.builder()
                .userId(userId)
                .achievement(firstAchievement)
                .build();
        firstUserAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(firstAchievement)
                .build();

    }

    @Test
    void testGetProgress_WithExistedProgress() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, firstAchievement.getId()))
                .thenReturn(Optional.ofNullable(firstAchievementProgress));

        assertEquals(firstAchievementProgress, achievementService.getProgress(userId, firstAchievement));
    }

    @Test
    void testGetProgress_CreatingNewProgress() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, firstAchievement.getId()))
                .thenReturn(Optional.empty());
        when(achievementProgressRepository.save(savedFirstAchievementProgress)).
                thenReturn(savedFirstAchievementProgress);

        assertEquals(savedFirstAchievementProgress, achievementService.getProgress(userId, firstAchievement));
    }

    @Test
    void testHasAchievement() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, firstAchievement.getId()))
                .thenReturn(true);

        achievementService.hasAchievement(userId, firstAchievement.getId());
    }

    @Test
    void testGiveAchievement() {
        when(userAchievementRepository.save(firstUserAchievement)).thenReturn(firstUserAchievement);

        achievementService.giveAchievement(userId, firstAchievement);

        verify(userAchievementRepository, times(1)).save(firstUserAchievement);
    }

}
