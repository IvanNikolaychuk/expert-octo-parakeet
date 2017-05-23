package com.stocks.core.api.quandl.java;

import org.apache.http.client.HttpResponseException;

import static com.google.common.base.Preconditions.checkNotNull;

public class InvalidTokenException extends Exception {
    private final String token;
    
    public InvalidTokenException(String token, HttpResponseException e) {
        super("Unable to authenticate with token '"+token+"'", e);
        this.token = checkNotNull(token);
    }
    
    public String getToken() {
        return token;
    }
}
