package com.tracker.dataservice.exceptions;

import java.util.Date;
import java.util.List;

public class ExceptionResponse {

    private Date timestamp;
    private String error;
    private List<String> details;

    public ExceptionResponse( Date timestamp, String error, List<String> details ) {

        super();
        this.timestamp = timestamp;
        this.error = error;
        this.details = details;
    }

    public Date getTimestamp() {

        return timestamp;
    }

    public String getError() {

        return error;
    }

    public List<String> getDetails() {

        return details;
    }

}