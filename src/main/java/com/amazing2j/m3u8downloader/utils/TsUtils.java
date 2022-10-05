package com.amazing2j.m3u8downloader.utils;

import com.amazing2j.m3u8downloader.download.Downloader;
import com.amazing2j.m3u8downloader.entity.ProxyEntity;
import com.amazing2j.m3u8downloader.entity.StorageEntity;

import java.io.File;

public class TsUtils {

    private final Downloader downloader;

    private final FileUtils fileUtils;

    public TsUtils(ProxyEntity proxyEntity, StorageEntity storageEntity) {
        if (proxyEntity.getHost() == null || proxyEntity.getHost().equals("")) {
            this.downloader = new Downloader();
        } else {
            this.downloader = new Downloader(proxyEntity.getHost(), proxyEntity.getPort());
        }
        this.fileUtils = new FileUtils(storageEntity.getVideoSavePath(), storageEntity.getM3u8SavePath(), storageEntity.getTsSavePath());
    }

    public byte[] downloadM3u8(String url) throws Exception {
        String fileName = UrlUtils.videoNameParser(url);
        byte[] bodyBytes = downloader.download(url);
        fileUtils.saveM3u8(fileName, bodyBytes);
        return bodyBytes;
    }

    public void download(String videoName, String url, byte[] keyBody, String iv) throws Exception {
        byte[] bodyBytes = downloader.download(url);
        if (keyBody != null) {
            bodyBytes = this.decrypt(bodyBytes, keyBody, iv);
        }
        fileUtils.saveVideo(videoName, bodyBytes, true);
    }

    public byte[] downloadTsKey(String url) throws Exception {
        return downloader.download(url);
    }

    public byte[] decrypt(byte[] content, byte[] keyBody, String iv) throws Exception {
        return AESUtils.decrypt(content, keyBody, iv);
    }

    public void clean(String videoName) {
        fileUtils.clean(videoName);
    }

    public File finish(String videoName) {
        return fileUtils.markVideoReady(videoName);
    }
}
