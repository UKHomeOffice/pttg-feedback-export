package uk.gov.digital.ho.pttg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class ResourceExceptionHandler {

    @ExceptionHandler(value = FeedbackException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity handleFeedbackxception(FeedbackException e) {
        log.error("FeedbackException: {}", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity handle(HttpClientErrorException e) {
        log.error("HttpClientErrorException: {}", e.getMessage());
        log.error("Error response body: {}", e.getResponseBodyAsString());
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(value = HttpServerErrorException.class)
    public ResponseEntity handle(HttpServerErrorException e) {
        log.error("HttpServerErrorException: {}", e.getMessage());
        log.error("Error response body: {}", e.getResponseBodyAsString());
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(value = RestClientException.class)
    public ResponseEntity handle(RestClientException e) {
        log.error("Error: {}", e.getMessage());
        log.error("Exception: ", e);
        return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
    }
}


