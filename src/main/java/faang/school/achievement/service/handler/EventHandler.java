package faang.school.achievement.service.handler;

import faang.school.achievement.service.Event.Event;

public interface EventHandler<T extends Event> {
    boolean canHandle(T event);
    void handleEvent(T event);

}