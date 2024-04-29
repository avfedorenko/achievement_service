package faang.school.achievement.service.handler;

public abstract class AbstractEventHandler<T> implements EventHandler<T>{
    @Override
    public void handle(T event){

    }

    @Override
    public boolean supportsEvent(Class<?> eventType){
        return false;
    }
}
