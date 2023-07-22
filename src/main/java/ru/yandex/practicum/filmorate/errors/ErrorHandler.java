package ru.yandex.practicum.filmorate.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.IncorrectPathVariableException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleObjectNotFoundException(final ObjectNotFoundException e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIncorrectPathVariableException(final IncorrectPathVariableException e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }
}
