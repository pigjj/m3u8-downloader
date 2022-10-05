package com.amazing2j.m3u8downloader.exception;


public class M3u8Exception extends RuntimeException {

    private String message;

    public M3u8Exception(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
