package faang.school.achievement.mapper;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserAchievementMapper{
    UserAchievement toUserAchievement(Achievement achievement);

}
