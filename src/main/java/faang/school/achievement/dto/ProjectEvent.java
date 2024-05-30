package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectEvent implements Event {

    Long authorId;
    Long projectId;

    @Override
    public long getAchievementHolderId() {
        return authorId;
    }
}

