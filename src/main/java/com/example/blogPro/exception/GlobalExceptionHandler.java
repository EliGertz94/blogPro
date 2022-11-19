package com.example.blogPro.exception;

import com.example.blogPro.DTO.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(ResourceNotFound.class)
        public ResponseEntity<ErrorDetails> resourceNotFoundHandler(ResourceNotFound exception,
                                                                    WebRequest webRequest){
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setMessage(exception.getMessage());
            errorDetails.setTimeStamp(new Date());
            errorDetails.setDetails(webRequest.getDescription(false));

            return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> blogAPIExceptionHandler(BlogAPIException exception,
                                                                WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setTimeStamp(new Date());
        errorDetails.setDetails(webRequest.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //for global exceptins
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handelGolbalExceptions(Exception exception,
                                                                WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setTimeStamp(new Date());
        errorDetails.setDetails(webRequest.getDescription(false));
        System.out.println("Exception was up");
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
