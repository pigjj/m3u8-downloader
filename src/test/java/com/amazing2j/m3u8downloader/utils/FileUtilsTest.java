package com.amazing2j.m3u8downloader.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    FileUtils fileUtils = new FileUtils("video", "m3u8s", "ts");

    @Test
    void markVideoReady() {
        fileUtils.markVideoReady("我们的歌");
    }
}