package com.amazing2j.m3u8downloader.service;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

/**
 * {@code @Author} HaiboJiang
 * {@code @Version} 1.0.0
 * {@code @ClassName} DiskInfoService.java
 * {@code @Description} // TODO
 * {@code @CreateTime} 2022年10月06日 18:19:00
 */
public class DiskInfoService {

    private final SystemInfo systemInfo = new SystemInfo();

    public double getDiskUsage() {
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        //总内存
        long totalByte = memory.getTotal();
        //剩余
        long availableByte = memory.getAvailable();
        return (totalByte - availableByte) * 1.0 / totalByte;
    }
}
