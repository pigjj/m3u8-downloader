package com.amazing2j.m3u8downloader.entity;


import java.util.List;

/**
 * 视频关联m3u8对象
 */
public class M3u8Entity {

    private String videoName;

    private String tsDlPrefix;

    private byte[] keyBody;

    private String iv;

    private String hlsUrl;

    private List<String> tsList;

    private String videoSavePath;

    private double videoSize;

    private String videoTitle;

    private String md5Str;


    public M3u8Entity(String videoName, String tsDlPrefix, byte[] keyBody, String iv, String hlsUrl, List<String> tsList, String videoSavePath, double videoSize, String videoTitle) {
        this.videoName = videoName;
        this.tsDlPrefix = tsDlPrefix;
        this.keyBody = keyBody;
        this.iv = iv;
        this.hlsUrl = hlsUrl;
        this.tsList = tsList;
        this.videoSavePath = videoSavePath;
        this.videoSize = videoSize;
        this.videoTitle = videoTitle;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getTsDlPrefix() {
        return tsDlPrefix;
    }

    public void setTsDlPrefix(String tsDlPrefix) {
        this.tsDlPrefix = tsDlPrefix;
    }

    public byte[] getKeyBody() {
        return keyBody;
    }

    public void setKeyBody(byte[] keyBody) {
        this.keyBody = keyBody;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public List<String> getTsList() {
        return tsList;
    }

    public void setTsList(List<String> tsList) {
        this.tsList = tsList;
    }

    public String getVideoSavePath() {
        return videoSavePath;
    }

    public void setVideoSavePath(String videoSavePath) {
        this.videoSavePath = videoSavePath;
    }

    public double getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(double videoSize) {
        this.videoSize = videoSize;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setMd5Str(String md5Str) {
        this.md5Str = md5Str;
    }

    public String getMd5Str() {
        return md5Str;
    }
}
