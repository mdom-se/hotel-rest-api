package com.demo.hotel.restcontroller;

import com.demo.hotel.webservice.client.dto.ResponseStatus;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.ws.client.WebServiceIOException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice
public class HotelRestControllerExceptionHandler {

    private static final Logger LOG = getLogger(HotelRestControllerExceptionHandler.class);

    enum ErrorMessage {
        CONTENT_TYPE_NOT_SUPPORTED("Content type not supported, please verify your request"),
        NETWORK("network error, please try again later"),
        UNEXPECTED("system error, please try again later");
        private final String message;
        ErrorMessage(String s) {
            this.message = s;
        }

        public String message(){
            return message;
        }

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStatus> unexpectedErrorException(Exception ex) {
        LOG.error("unexpectedErrorException message: {}", ex.getMessage());
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseStatus.setMessage(ErrorMessage.UNEXPECTED.message);
        return ResponseEntity.internalServerError().body(responseStatus);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseStatus> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        LOG.error("httpMediaTypeNotSupportedException message: {}", ex.getMessage());
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatusCode(HttpStatus.BAD_REQUEST.value());
        responseStatus.setMessage(ErrorMessage.CONTENT_TYPE_NOT_SUPPORTED.message);
        return ResponseEntity.internalServerError().body(responseStatus);
    }

    @ExceptionHandler({ConnectException.class, SocketTimeoutException.class, WebServiceIOException.class})
    public ResponseEntity<ResponseStatus> connectionException(Exception ex) {
        LOG.error("connectionException message: {}", ex.getMessage());
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseStatus.setMessage(ErrorMessage.NETWORK.message);
        return ResponseEntity.internalServerError().body(responseStatus);
    }


}
