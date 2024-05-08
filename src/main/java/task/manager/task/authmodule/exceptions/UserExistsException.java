package task.manager.task.authmodule.exceptions;

public class UserExistsException extends RuntimeException{
    public UserExistsException() {
        super();
    }

    public UserExistsException(String message) {
        super(message);
    }
}
