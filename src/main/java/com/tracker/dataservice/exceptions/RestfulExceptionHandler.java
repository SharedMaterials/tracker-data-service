package com.tracker.dataservice.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RestController
public class RestfulExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    public RestfulExceptionHandler() {

        super();
    }

    @ExceptionHandler( EntityNotFoundException.class )
    protected ResponseEntity<?> handleEntityNotFound( EntityNotFoundException ex ) throws JsonProcessingException {

        List<String> details = new ArrayList<>();
        details.add( getRootCause( ex ).getMessage() );
        ObjectMapper mapper = new ObjectMapper();
        log.error( mapper.writeValueAsString( generateErrorResponse( "Entity Not Found", details) ) );
        return ResponseEntity.noContent().build();
    }

    /*
     * Get Error Message
     * */
    private static Throwable getRootCause( Throwable throwable ) {

        if ( throwable.getCause() != null ) {

            return getRootCause( throwable.getCause() );
        }

        return throwable;
    }

    private ExceptionResponse generateErrorResponse( String cause, List<String> details ) {

        return new ExceptionResponse( new Date(), cause, details );
    }

}
