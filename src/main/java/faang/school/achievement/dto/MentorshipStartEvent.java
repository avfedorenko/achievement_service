package faang.school.achievement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MentorshipStartEvent implements Event{

    @NotNull
    private long mentorId;
    @NotNull
    private long menteeId;


    @Override
    public long getAchievementHolderId(){
        return menteeId;
    }
}
