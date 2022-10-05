package com.amazing2j.m3u8downloader.entity;

/**
 * 文件保存配置对象
 */
public class StorageEntity {

    private String videoSavePath;
    private String m3u8SavePath;
    private String tsSavePath;

    public StorageEntity(String videoSavePath, String m3u8SavePath, String tsSavePath) {
        this.videoSavePath = videoSavePath;
        this.m3u8SavePath = m3u8SavePath;
        this.tsSavePath = tsSavePath;
    }

    public String getVideoSavePath() {
        return videoSavePath;
    }

    public void setVideoSavePath(String videoSavePath) {
        this.videoSavePath = videoSavePath;
    }

    public String getM3u8SavePath() {
        return m3u8SavePath;
    }

    public void setM3u8SavePath(String m3u8SavePath) {
        this.m3u8SavePath = m3u8SavePath;
    }

    public String getTsSavePath() {
        return tsSavePath;
    }

    public void setTsSavePath(String tsSavePath) {
        this.tsSavePath = tsSavePath;
    }
}
