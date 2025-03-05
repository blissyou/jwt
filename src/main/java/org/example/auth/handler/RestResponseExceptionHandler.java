package org.example.auth.handler;

import org.example.auth.dto.ErrorDto;
import org.example.auth.exception.DuplicateMemberException;
import org.example.auth.exception.NotFoundMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = {DuplicateMemberException.class})
    @ResponseBody
    public ErrorDto conflict(RuntimeException ex , WebRequest request) {
        return new ErrorDto(CONFLICT.value() , ex.getMessage());

    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = {NotFoundMemberException.class})
    @ResponseBody
    protected ErrorDto forbidden(RuntimeException ex, WebRequest request) {
        return new ErrorDto(FORBIDDEN.value(), ex.getMessage());
    }
}
