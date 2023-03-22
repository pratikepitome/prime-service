package com.nw.prime.exception;

import com.nw.prime.model.ErrorDetails;
import com.nw.prime.model.PrimeNumberErrorResponse;
import com.nw.prime.model.PrimeNumberSuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@ControllerAdvice
@Slf4j
public class PrimeNumberExceptionResponseHandler
        extends ResponseEntityExceptionHandler {


    /***
     *
     * This method intercepts Exception thrown during execution of code and
     * return BAD_REQUEST status code
     * @param ex Exception caught during execution of request
     * @param request the spring context request
     * @return ResponseEntity<Object> containing details about exception
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handlePrimeNumberRelatedException(
            RuntimeException ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, generateBadResponse(ex, request),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = Error.class)
    protected ResponseEntity<Object> handleAnyException(
            Error ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        RuntimeException rt = new RuntimeException(ex.getMessage());
        return handleExceptionInternal(rt, generateBadResponse(rt, request),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private PrimeNumberErrorResponse generateBadResponse(Exception e, WebRequest request) {
        return new PrimeNumberErrorResponse(new ErrorDetails(e.getMessage()));
    }

}
