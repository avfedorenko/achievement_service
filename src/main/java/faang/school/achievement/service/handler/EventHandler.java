package faang.school.achievement.service.handler;


import faang.school.achievement.dto.Event;

public interface EventHandler<T extends Event> {
    boolean canHandle(T event);
    void handleEvent(T event);
}
