package com.in28minutes.rest.webservices.restfulwebservices.exception;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler
/* extends ResponseEntityExceptionHandler */ {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex,
	    WebRequest request) {
	ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
		ex.getMessage(), request.getDescription(false));

	return new ResponseEntity<ErrorDetails>(errorDetails,
		HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(
	    Exception ex, WebRequest request) {
	ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
		ex.getMessage(), request.getDescription(false));

	return new ResponseEntity<ErrorDetails>(errorDetails,
		HttpStatus.NOT_FOUND);
    }

//    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
	    MethodArgumentNotValidException ex, HttpHeaders headers,
	    HttpStatusCode status, WebRequest request) {

	ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
		"Total Errors: " + ex.getErrorCount() + " First Error: "
			+ ex.getFieldError().getDefaultMessage(),
		request.getDescription(false));

	return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<Object> handleMethodArgumentNotValid2(MethodArgumentNotValidException ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("error", ex);
//        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
//    }

}
