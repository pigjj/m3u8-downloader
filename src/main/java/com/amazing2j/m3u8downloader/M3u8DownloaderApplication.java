package com.amazing2j.m3u8downloader;

import com.amazing2j.m3u8downloader.download.M3u8Downloader;
import com.amazing2j.m3u8downloader.entity.ProxyEntity;
import com.amazing2j.m3u8downloader.entity.StorageEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class M3u8DownloaderApplication {

    public static void main(String[] args) {

        ProxyEntity proxy = new ProxyEntity();
        StorageEntity storageEntity = new StorageEntity("C:\\Users\\haibo\\Downloads\\jabledl\\video", "C:\\Users\\haibo\\Downloads\\jabledl\\m3u8s", "C:\\Users\\haibo\\Downloads\\jabledl\\ts");

        M3u8Downloader m3u8Downloader = new M3u8Downloader(proxy, storageEntity);

        try {
            m3u8Downloader.download("M3U8测试", "http://playertest.longtailvideo.com/adaptive/bipbop/gear4/prog_index.m3u8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SpringApplication.run(M3u8DownloaderApplication.class, args);
    }

}
