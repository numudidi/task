package task.manager.task.authmodule.exceptions;

public class InvalidValidationKeyException extends RuntimeException{
    public InvalidValidationKeyException() {
        super();
    }

    public InvalidValidationKeyException(String message) {
        super(message);
    }
}
