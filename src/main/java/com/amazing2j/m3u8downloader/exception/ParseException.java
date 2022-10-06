package com.amazing2j.m3u8downloader.exception;


public class ParseException extends RuntimeException {

    private String message;

    public ParseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
