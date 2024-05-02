package faang.school.achievement.mapper;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAchievementMapper {
    UserAchievement toUserAchievement(Achievement achievement);
}
