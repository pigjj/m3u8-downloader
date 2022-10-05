package com.amazing2j.m3u8downloader.utils;

public class UrlUtils {

    static final String DELIMITER = "/";

    public static String videoNameParser(String url) {
        String[] urlParts = url.split(UrlUtils.DELIMITER);
        return urlParts[urlParts.length - 1];
    }

    public static String xKeyParser(String url) {
        int index = url.lastIndexOf(UrlUtils.DELIMITER);
        return url.substring(0, index);
    }
}
