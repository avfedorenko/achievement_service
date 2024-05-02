package faang.school.achievement.dto;

import faang.school.achievement.events.Event;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowerEvent implements Event {
    @NotNull
    private long follower_id;
    @NotNull
    private long followee_id;
    @NotNull
    private LocalDateTime subscriptionDateTime;

    @Override
    public String getEventType(){
        return "FollowerEvent";
    }

    @Override
    public long getUserId(){
        return follower_id;
    }
}
