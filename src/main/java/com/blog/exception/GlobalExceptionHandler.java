package com.blog.exception;

import com.blog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice //is a specialized annotation which allows handling exceptions across the whole application in one global handling component.
// when any part of our project if exception is occurring that exception is given to this class by the help of @ControllerAdvice and its work like catch block
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {// here we create a globalExceptionHandler class in exception package and that class should extend ResponseEntityExceptionHandler class

    @ExceptionHandler(ResourceNotFoundException.class)// if exception occur because of the ResourceNotFoundException class objects and that class object address is coming to here the object reference variable
    public ResponseEntity<ErrorDetails>handleResourceNotFoundException(// this method will handle specific kind of exception like resourceNotFoundException are like post not found, comment not found
            ResourceNotFoundException exception,
            WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),  // here we are initializing the errorDetail constructor Date, exception.message and get description
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(// this method will handel any kind of exception not just the specific exception
            Exception exception,WebRequest webRequest){
         ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                 webRequest.getDescription(false));
         return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
