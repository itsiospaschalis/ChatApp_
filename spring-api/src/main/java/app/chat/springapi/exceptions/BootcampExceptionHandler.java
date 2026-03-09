package app.chat.springapi.exceptions;

import app.chat.springapi.exceptions.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BootcampExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity <ErrorMessage> handleException(Exception e) {

        //Set the status handconded to Internal server error 500
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (e instanceof BootcampException) {
            BootcampException bootcampException = (BootcampException) e;
            status = bootcampException.getHttpStatus();
        }
        ErrorMessage errorMessage= new ErrorMessage();
        errorMessage.setErrorCode(status.value());
        errorMessage.setErrorMessage("Something went wrong: "+e.getMessage());;


        return ResponseEntity.status(status).body(errorMessage);


    }
}