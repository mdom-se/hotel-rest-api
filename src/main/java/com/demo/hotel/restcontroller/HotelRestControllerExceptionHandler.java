package com.demo.hotel.restcontroller;

import com.demo.hotel.webservice.client.dto.ResponseStatus;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice
public class HotelRestControllerExceptionHandler {

    private static final Logger LOG = getLogger(HotelRestControllerExceptionHandler.class);

    static final String CONTENT_TYPE_NOT_SUPPORTED_ERROR = "Content type not supported";
    static final String UNEXPECTED_ERROR = "unexpected internal error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStatus> unexpectedErrorException(Exception ex) {
        LOG.error("unexpectedErrorException message: {}", ex.getMessage());
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseStatus.setMessage(UNEXPECTED_ERROR);
        return ResponseEntity.internalServerError().body(responseStatus);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseStatus> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        LOG.error("httpMediaTypeNotSupportedException message: {}", ex.getMessage());
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatusCode(HttpStatus.BAD_REQUEST.value());
        responseStatus.setMessage(CONTENT_TYPE_NOT_SUPPORTED_ERROR);
        return ResponseEntity.internalServerError().body(responseStatus);
    }
}
