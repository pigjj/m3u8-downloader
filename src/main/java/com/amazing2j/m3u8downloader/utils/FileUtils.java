package com.amazing2j.m3u8downloader.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private final Logger log = LoggerFactory.getLogger(FileUtils.class);

    private final String videoSavePath;
    private final String m3u8SavePath;
    private final String tsSavePath;

    public FileUtils(String videoSavePath, String m3u8SavePath, String tsSavePath) {
        this.videoSavePath = videoSavePath;
        this.m3u8SavePath = m3u8SavePath;
        this.tsSavePath = tsSavePath;
    }

    /**
     * 清理未下载完成的视频
     *
     * @param fileName 视频名称
     */
    public void clean(String fileName) {
        String filePath = String.format("%s%s%s", videoSavePath, File.separator, fileName);
        File f = new File(filePath);
        if (f.exists()) {
            boolean success = f.delete();
            if (!success) {
                log.warn("清理视频文件失败!");
            }
        }
    }

    /**
     * 标记下载文件成功, 将 xxx.mp4.temp 文件 重命名为 xxx.mp4
     *
     * @param fileName 文件名称
     * @return 视频文件对象
     */
    public File markVideoReady(String fileName) {
        String filePath = String.format("%s%s%s.mp4.temp", videoSavePath, File.separator, fileName);
        File tempFile = new File(filePath);
        if (!tempFile.isFile()) {
            log.error("已下载的视频文件不存在: {}", filePath);
            return tempFile;
        }
        if (!fileName.endsWith(".mp4")) {
            fileName += ".mp4";
        }
        filePath = String.format("%s%s%s", videoSavePath, File.separator, fileName);
        File mp4File = new File(filePath);
        boolean success = tempFile.renameTo(mp4File);
        log.warn("重命名文件: {}, 结果: {}", fileName, success);
        return mp4File;
    }

    /**
     * 保存视频文件函数
     *
     * @param fileName 文件名称
     * @param content  文件二进制数据流
     * @param isAppend 文件写入模式, true: 写文件追加, false: 覆盖写入
     */
    public void saveVideo(String fileName, byte[] content, boolean isAppend) {
        File file = new File(videoSavePath);
        if (!file.isDirectory()) {
            boolean result = file.mkdir();
            log.info("创建文件夹: {} 结果: {}", videoSavePath, result);
        }
        String filePath = String.format("%s%s%s", videoSavePath, File.separator, fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath, isAppend)) {
            fileOutputStream.write(content);
        } catch (IOException e) {
            log.error("保存文件: {}, 异常: {}", filePath, e.getMessage());
        }
    }

    /**
     * 保存M3U8文件
     *
     * @param fileName 文件名称
     * @param content  文件二进制流
     */
    public void saveM3u8(String fileName, byte[] content) {
        File file = new File(m3u8SavePath);
        if (!file.isDirectory()) {
            boolean result = file.mkdir();
            log.info("创建文件夹: {} 结果: {}", m3u8SavePath, result);
        }
        if (!fileName.endsWith(".m3u8")) {
            fileName += ".m3u8";
        }
        String filePath = String.format("%s%s%s", m3u8SavePath, File.separator, fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(content);
        } catch (IOException e) {
            log.error("保存文件: {}, 异常: {}", filePath, e.getMessage());
        }
    }

    /**
     * 保存TS文件
     *
     * @param videoName 视频名称
     * @param fileName  文件名称
     * @param content   TS文件二进制流
     * @throws IOException IO异常
     */
    public void saveTs(String videoName, String fileName, byte[] content) throws IOException {
        File file = new File(tsSavePath);
        if (!file.isDirectory()) {
            boolean result = file.mkdir();
            log.info("创建文件夹: {} 结果: {}", tsSavePath, result);
        }
        String fileDirPath = String.format("%s%s%s", tsSavePath, File.separator, videoName);
        file = new File(fileDirPath);
        if (!file.isDirectory()) {
            boolean success = file.mkdir();
            if (!success) {
                throw new IOException(String.format("创建文件夹失败: %s", fileDirPath));
            }
        }
        if (!fileName.endsWith(".ts")) {
            fileName += ".ts";
        }
        String filePath = String.format("%s%s%s", fileDirPath, File.separator, fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(content);
        } catch (IOException e) {
            log.error("保存文件: {}, 异常: {}", filePath, e.getMessage());
        }
    }

    /**
     * 读取TS文件
     *
     * @param videoName 视频名称
     * @param fileName  TS文件名称
     * @return TS文件二进制流
     * @throws IOException IO异常
     */
    public byte[] readTs(String videoName, String fileName) throws IOException {
        String filePath = String.format("%s%s%s%s%s.ts", tsSavePath, File.separator, videoName, File.separator, fileName);
        return this.read(filePath);
    }

    /**
     * 判断指定视频的TS文件是否存在
     *
     * @param videoName 视频名称
     * @param fileName  文件名称
     * @return true: 存在, false: 不存在 (下载TS文件只有成功之后才会写入, 因此只要TS文件存在说明TS文件可用)
     */
    public boolean tsExists(String videoName, String fileName) {
        if (!fileName.endsWith(".ts")) {
            fileName += ".ts";
        }
        String filePath = String.format("%s%s%s%s%s", tsSavePath, File.separator, videoName, File.separator, fileName);
        return new File(filePath).exists();
    }

    /**
     * 通用文件读取函数
     *
     * @param filePath 文件路径
     * @return 文件二进制流
     * @throws IOException IO异常
     */
    public byte[] read(String filePath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * 扫描指定路径下所有的文件
     *
     * @param targetPath 指定路径
     * @return 文件对象列表
     */
    public List<String> getFiles(String targetPath) {
        List<String> files = new ArrayList<>();
        File file = new File(targetPath);
        File[] fileList = file.listFiles();
        for (File f : fileList) {
            if (f.isFile()) {
                files.add(f.getName());
            }
        }
        return files;
    }
}
