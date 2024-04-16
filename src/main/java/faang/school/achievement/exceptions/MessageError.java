package faang.school.achievement.exceptions;

public enum MessageError {
    ACHIEVEMENT_NOT_FOUND_IN_CACHE("Achievement is not found in cache"),
    ACHIEVEMENT_NOT_FOUND_IN_ACHIEVEMENT_REPOSITORY("Achievement is not found in repository")
    ;

    private final String message;
    MessageError(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
