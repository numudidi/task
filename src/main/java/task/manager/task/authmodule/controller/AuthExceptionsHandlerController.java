package task.manager.task.authmodule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import task.manager.task.authmodule.exceptions.InvalidValidationKeyException;
import task.manager.task.authmodule.exceptions.UserExistsException;
import task.manager.task.model.dto.GenericResponse;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthExceptionsHandlerController {
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<GenericResponse> handleEmailAndPhoneDuplicateException(UserExistsException ex) {
        return new ResponseEntity<>(GenericResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidValidationKeyException.class)
    public ResponseEntity<GenericResponse> handleEmailAndPhoneDuplicateException(InvalidValidationKeyException ex) {
        return new ResponseEntity<>(GenericResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (BindException.class)
    public ResponseEntity<GenericResponse>  handleInvalidArgument(BindException ex) {
        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error-> {
            errorMap.put(error.getField(), error.getDefaultMessage ());
        });

        return new ResponseEntity<>(GenericResponse.builder()
                .data(errorMap)
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed")
                .build(), HttpStatus.BAD_REQUEST);
    }
}
