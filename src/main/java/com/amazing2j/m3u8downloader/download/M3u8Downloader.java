package com.amazing2j.m3u8downloader.download;

import com.amazing2j.m3u8downloader.entity.M3u8Entity;
import com.amazing2j.m3u8downloader.entity.ProxyEntity;
import com.amazing2j.m3u8downloader.entity.StorageEntity;
import com.amazing2j.m3u8downloader.utils.TsUtils;
import com.amazing2j.m3u8downloader.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * M3U8下载器
 */
public class M3u8Downloader {

    private final Logger log = LoggerFactory.getLogger(M3u8Downloader.class);

    private final TsUtils tsUtils;

    public M3u8Downloader(ProxyEntity proxyEntity, StorageEntity storageEntity) {
        this.tsUtils = new TsUtils(proxyEntity, storageEntity);
    }

    public TsUtils getTsUtils() {
        return tsUtils;
    }

    /**
     * 下载函数
     *
     * @param videoName 视频名称
     * @param m3u8Url   m3u8下载地址
     * @throws Exception 下载异常
     */
    public void downloadM3u8(String videoName, String m3u8Url) throws Exception {
        byte[] body = tsUtils.downloadM3u8(m3u8Url);
        String m3u8ContentStr = new String(body);
        String[] lines = m3u8ContentStr.split("\n");
        List<String> tsList = new ArrayList<>();
        String keyLine = null;
        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].startsWith("#")) {
                tsList.add(lines[i]);
            }
            String x_KEY = "#EXT-X-KEY";
            if (lines[i].contains(x_KEY)) {
                keyLine = lines[i];
            }
        }
        String urlPrefix = UrlUtils.xKeyParser(m3u8Url);
        M3u8Entity m3u8Entity;

        String[] keyLines = null;

        if (keyLine != null) {
            keyLines = keyLine.split(",");
            if (keyLines.length < 2) {
                keyLines = null;
            }
        }

        if (keyLines != null) {
            String xKey = this.parseXKey(keyLines);
            String iv = this.parseIV(keyLine);

            String xKeyDlUrl = String.format("%s%s%s", urlPrefix, File.separator, xKey);

            byte[] keyBody = tsUtils.downloadTsKey(xKeyDlUrl);

            m3u8Entity = new M3u8Entity(videoName, urlPrefix, keyBody, iv, m3u8Url, tsList, null, 0, null);
        } else {
            m3u8Entity = new M3u8Entity(videoName, urlPrefix, null, null, m3u8Url, tsList, null, 0, null);
        }

        this.downloadTs(m3u8Entity);
    }

    /**
     * 下载视频关联的所有TS文件
     *
     * @param m3u8Entity {@link M3u8Entity}
     */
    private void downloadTs(M3u8Entity m3u8Entity) {
        List<String> tsList = m3u8Entity.getTsList();
        if (tsList == null) {
            return;
        }
        tsUtils.clean(m3u8Entity.getVideoName());
        for (int i = 0; i < tsList.size(); i++) {
            String dlUrl;
            if (tsList.get(i).startsWith("http") || tsList.get(i).startsWith("https")) {
                dlUrl = tsList.get(i);
            } else {
                dlUrl = String.format("%s/%s", m3u8Entity.getTsDlPrefix(), tsList.get(i));
            }
            log.info("视频: {}, 一共: {} 个ts, 正在下载第: {} 个ts, 地址为: {}", m3u8Entity.getVideoName(), tsList.size(), i, dlUrl);
            try {
                tsUtils.download(m3u8Entity.getVideoName(), dlUrl, m3u8Entity.getKeyBody(), m3u8Entity.getIv());
            } catch (Exception e) {
                log.error("TS文件不存在: {}", dlUrl);
            }
        }

        File file = tsUtils.finish(m3u8Entity.getVideoName());
        log.info("当前时间: {}, 下载完成: {}, 总大小为: {}GB", LocalDateTime.now(), m3u8Entity.getVideoName(), (double) (file.length() / (1024 * 1024 * 1024)));
        m3u8Entity.setVideoSavePath(file.getPath());
        m3u8Entity.setVideoSize(file.length());
    }

    /**
     * 如果M3U8被加密, 解析M3U8文件中的加密Key下载路径
     *
     * @param keyLines 包含key路径的m3u8行内容
     * @return 加密key
     */
    private String parseXKey(String[] keyLines) {
        String keyFileName = keyLines[1];
        keyFileName = keyFileName.replace("URI=", "");
        return keyFileName.replace("\"", "");
    }

    /**
     * 解析IV字段
     *
     * @param keyLine 包含key路径的m3u8行内容
     * @return IV字段
     */
    private String parseIV(String keyLine) {
        if (keyLine == null) {
            return null;
        }
        String[] keyLines = keyLine.split(",");
        String keyFileName = keyLines[2];
        return keyFileName.replace("IV=", "");
    }
}
