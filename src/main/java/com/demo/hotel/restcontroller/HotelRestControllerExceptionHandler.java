package com.demo.hotel.restcontroller;

import com.demo.hotel.webservice.client.dto.ResponseStatus;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice
public class HotelRestControllerExceptionHandler {

    private static final Logger LOG = getLogger(HotelRestControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStatus> resourceNotFoundException(Exception ex, WebRequest request) {
        LOG.error("Unexpected error in: {} message: {}", request.getContextPath(), ex.getMessage());
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseStatus.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name());
        return ResponseEntity.internalServerError().body(responseStatus);
    }
}
