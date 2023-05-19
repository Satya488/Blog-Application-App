package com.blog.payload;

import java.util.Date;

public class ErrorDetails {

    private Date timestamp;// Date we import in java.util package
    private String message;   // this all we want to give to the end user in frontend
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {// here we used constructor based injection and getters method for data encapsulation
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
