package faang.school.achievement.exceptions;

public class AchievementNotFoundException extends RuntimeException{
    public AchievementNotFoundException(MessageError messageError){
        super(messageError.getMessage());
    }
}
