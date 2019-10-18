package com.tw.apistackbase.advice;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NonUniqueResultException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseError notFoundException(NotFoundException exception){
        ResponseError error = new ResponseError();
        error.setCode(404);
        error.setMessage(exception.getMessage());
        return error;
    }

    @ResponseBody
    @ExceptionHandler(NonUniqueResultException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseError nonUniqueException(NonUniqueResultException exception){
        ResponseError error = new ResponseError();
        error.setCode(500);
        error.setMessage(exception.getMessage());
        return error;
    }
}
