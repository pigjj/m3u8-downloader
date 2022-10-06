package com.amazing2j.m3u8downloader;

import com.amazing2j.m3u8downloader.download.M3u8Downloader;
import com.amazing2j.m3u8downloader.entity.ProxyEntity;
import com.amazing2j.m3u8downloader.entity.StorageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class M3u8DownloaderApplication {

    public static void main(String[] args) throws FileNotFoundException {
        final Logger log = LoggerFactory.getLogger(M3u8DownloaderApplication.class);

        log.info(System.getProperty("user.dir"));

        String dlListConfigPath = System.getProperty("user.dir") + File.separator + "dl-list.yml";
        List<Map<String, String>> dlList = new Yaml().load(new FileInputStream(new File(dlListConfigPath)));
        log.info("{}", dlList);

        ProxyEntity proxy = new ProxyEntity();
        StorageEntity storageEntity = new StorageEntity("video", "m3u8s", "ts");

        M3u8Downloader m3u8Downloader = new M3u8Downloader(proxy, storageEntity);

        for (int i = 0; i < dlList.size(); i++) {
            Map<String, String> dlMap = dlList.get(i);
            try {
                String videoName = dlMap.get("videoName");
                String videoUrl = dlMap.get("videoUrl");
                log.info("下载第{}个视频: {}, 地址: {}", i, videoName, videoUrl);
                m3u8Downloader.downloadM3u8(videoName, videoUrl);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
