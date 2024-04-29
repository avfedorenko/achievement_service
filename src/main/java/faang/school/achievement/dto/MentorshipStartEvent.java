package faang.school.achievement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorshipStartEvent{

    @NotNull
    private long mentor_id;
    @NotNull
    private long mentee_id;

}
