package faang.school.achievement.dto;

import faang.school.achievement.dto.event.Event;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorshipStartEvent implements Event{

    @NotNull
    private long mentor_id;
    @NotNull
    private long mentee_id;

    @Override
    public String getEventType(){
        return "MentorshipStartEvent";
    }

    @Override
    public long getUserId(){
        return mentee_id;
    }
}
