package edu.bbte.idde.bmim2214.controller;

import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Stream;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final Stream<String> handleConstraintViolation(ConstraintViolationException e) {
        log.debug("ConstraintViolationException occurred", e);
        return e.getConstraintViolations().stream()
                .map(it -> it.getPropertyPath().toString() + " " + it.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final Stream<String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.debug("MethodArgumentNotValidException occurred", e);
        return e.getBindingResult().getFieldErrors().stream()
                .map(it -> it.getField() + " " + it.getDefaultMessage());
    }

    @ExceptionHandler(CarExceptionDatabase.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleCarExceptionDatabase(CarExceptionDatabase e) {
        return e.getMessage();
    }

    @ExceptionHandler(CarExceptionDates.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleCarExceptionDatabase(CarExceptionDates e) {
        return e.getMessage();
    }
}
