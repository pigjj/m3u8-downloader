package com.amazing2j.m3u8downloader.exception;

public class DownloadException extends RuntimeException {

    private String message;

    public DownloadException(String message) {
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
