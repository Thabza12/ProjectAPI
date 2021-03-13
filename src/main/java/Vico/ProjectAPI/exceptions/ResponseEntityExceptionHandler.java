package Vico.ProjectAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@RestController
class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler
//    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex, WebRequest request){
//        ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
//        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(value = {ProjectIdException.class})
    public ResponseEntity<Object> handleProjectIdException(ProjectIdException e){
        //Create payload containing exception details
        //return response entity
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ProjectIdExceptionResponse projectIdExceptionResponse = new ProjectIdExceptionResponse(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(projectIdExceptionResponse, badRequest);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex, WebRequest request){
        UsernameAlreadyExistsResponse exceptionResponse = new UsernameAlreadyExistsResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }



}
