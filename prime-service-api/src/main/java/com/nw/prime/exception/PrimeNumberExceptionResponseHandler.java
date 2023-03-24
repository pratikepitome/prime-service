package com.nw.prime.exception;

import com.nw.prime.model.ErrorDetails;
import com.nw.prime.model.PrimeNumberErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.TimeoutException;

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

        return handleExceptionInternal(ex, generateBadResponse(ex),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * This method intercepts Timeout exception
     * @param ex TimeoutException
     * @param request the spring context request
     * @return ResponseEntity<Object> containing details about exception
     */
    @ExceptionHandler(value = TimeoutException.class)
    protected ResponseEntity<Object> handleTimeOutException(
            TimeoutException ex, WebRequest request) {
        String errorMessage =  "Could not process request as number is too large";
        log.warn(errorMessage, ex);
        RuntimeException rt = new RuntimeException(errorMessage);
        return handleExceptionInternal(rt, generateBadResponse(rt),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * This method intercepts Any exception which could not be handled by above exception Handler
     * @param ex Exception
     * @param request the spring context request
     * @return ResponseEntity<Object> containing details about exception
     */
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleAnyException(
            Exception ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        RuntimeException rt = new RuntimeException(ex.getMessage());
        return handleExceptionInternal(rt, generateBadResponse(rt),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private PrimeNumberErrorResponse generateBadResponse(Exception e) {
        return new PrimeNumberErrorResponse(new ErrorDetails(e.getMessage()));
    }

}
