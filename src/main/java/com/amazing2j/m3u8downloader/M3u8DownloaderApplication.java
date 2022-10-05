package com.amazing2j.m3u8downloader;

import com.amazing2j.m3u8downloader.download.M3u8Downloader;
import com.amazing2j.m3u8downloader.entity.ProxyEntity;
import com.amazing2j.m3u8downloader.entity.StorageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class M3u8DownloaderApplication {

    public static void main(String[] args) {
        final Logger log = LoggerFactory.getLogger(M3u8DownloaderApplication.class);

        String videoName;
        String m3u8Url;
        Scanner scan = new Scanner(System.in);
        System.out.print("视频名称: ");
        videoName = scan.nextLine();
        System.out.print("M3U8文件地址: ");
        m3u8Url = scan.nextLine();
        log.info("视频名称: {}, M3U8地址: {}", videoName, m3u8Url);

        if (videoName == null || videoName.equals("") || m3u8Url == null || m3u8Url.equals("")) {
            log.error("视频名称或这M3U8文件地址为空");
            return;
        }

        ProxyEntity proxy = new ProxyEntity();
        StorageEntity storageEntity = new StorageEntity("video", "m3u8s", "ts");

        M3u8Downloader m3u8Downloader = new M3u8Downloader(proxy, storageEntity);

        try {
            m3u8Downloader.download(videoName, m3u8Url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
