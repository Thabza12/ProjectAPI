package Vico.ProjectAPI.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ProjectIdException extends RuntimeException {

    public ProjectIdException(String s) {
        super(s);
    }
}
